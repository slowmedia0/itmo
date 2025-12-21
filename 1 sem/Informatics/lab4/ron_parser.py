class RonParser:
    def __init__(self, text):  # Конструктор
        self.text = text  # Сохраняем текст в промежуточную строку
        self.pos = 0  # Позиция в тексте промежуточной строки

    def skip_whitespace(self):  #Функция, которая скипает WHITESPACE и Comments
        while self.pos < len(self.text):
            ch = self.text[self.pos]

            # Пропускаем обычные пробелы
            if ch.isspace():
                self.pos += 1
                continue

            # Однострочный комментарий: // до конца строки
            if ch == '/' and self.pos + 1 < len(self.text) and self.text[self.pos + 1] == '/':
                self.pos += 2
                while self.pos < len(self.text) and self.text[self.pos] != '\n':
                    self.pos += 1
                continue

            # Многострочный комментарий: /* ... */
            if ch == '/' and self.pos + 1 < len(self.text) and self.text[self.pos + 1] == '*':
                self.pos += 2
                while self.pos + 1 < len(self.text):
                    if self.text[self.pos] == '*' and self.text[self.pos + 1] == '/':
                        self.pos += 2
                        break
                    self.pos += 1
                else:
                    raise ValueError("Не закрыт многострочный комментарий /*")
                continue
            #В ином случае
            break

    def parse_ident(self):  # Метод для чтения идентификатора
        self.skip_whitespace()  #Пропусаем WHITESPACE
        start = self.pos
        if self.pos >= len(self.text) or not (self.text[self.pos].isalpha() or self.text[self.pos] == '_'):  # Проверяем: есть ли символ и начинается ли с буквы или _
            raise ValueError(f"Ожидался идентификатор на позиции {self.pos}")#Ошибка
        self.pos += 1  # Переходим к следующему символу
        while self.pos < len(self.text) and (self.text[self.pos].isalnum() or self.text[self.pos] == '_'):  # Пока символ — буква, цифра или _
            self.pos += 1
        return self.text[start:self.pos] #Возвращаем идентификатор

    def parse_string(self):  # Метод для чтения строки — это текст в двойных кавычках "текст"
        self.skip_whitespace() #Пропусаем WHITESPACE 
        if self.pos >= len(self.text) or self.text[self.pos] != '"':  # Должна начинаться с "
            raise ValueError(f"Ожидалась кавычка на позиции {self.pos}")  # Ошибка
        self.pos += 1  #Переходим за кавычку
        start = self.pos  #Запоминаем начало текста внутри
        while self.pos < len(self.text) and self.text[self.pos] != '"':  # Пока не найдём закрывающую кавычку
            self.pos += 1
        if self.pos >= len(self.text):  # Если текст закончился, а кавычка не найдена
            raise ValueError("Не закрыта строка")  # Ошибка
        value = self.text[start:self.pos]  # Берём текст между кавычками
        self.pos += 1  # Переходим за закрывающую кавычку
        return value  # Возвращаем строку

    def parse_number(self):
        self.skip_whitespace()
        start = self.pos

        # Минус в начале
        if self.pos < len(self.text) and self.text[self.pos] == '-':
            self.pos += 1

        # Проверяем префикс системы счисления
        base = 10  # По умолчанию — десятичная
        if self.pos + 1 < len(self.text) and self.text[self.pos] == '0':
            next_ch = self.text[self.pos + 1].lower()
            if next_ch == 'b':  # Двоичная: 0b1010
                base = 2
                self.pos += 2
            elif next_ch == 'o':  # Восьмеричная: 0o755
                base = 8
                self.pos += 2
            elif next_ch == 'x':  # Шестнадцатеричная: 0xFF
                base = 16
                self.pos += 2

        # Читаем цифры в зависимости от системы счисления
        digits = '0123456789abcdef'  # Для проверки в hex
        has_digits = False
        while self.pos < len(self.text):
            ch = self.text[self.pos].lower()
            if ch.isdigit() or (base == 16 and ch in 'abcdef'):
                if base == 2 and ch not in '01':
                    raise ValueError("В двоичном числе только 0 и 1")
                if base == 8 and ch not in '01234567':
                    raise ValueError("В восьмеричном числе только 0–7")
                self.pos += 1
                has_digits = True
            else:
                break

        if not has_digits:
            raise ValueError("Ожидались цифры после префикса")

        # Дробная часть (только для десятичной системы)
        has_dot = False
        if base == 10 and self.pos < len(self.text) and self.text[self.pos] == '.':
            has_dot = True
            self.pos += 1
            if self.pos >= len(self.text) or not self.text[self.pos].isdigit():
                raise ValueError("После точки ожидаются цифры")
            while self.pos < len(self.text) and self.text[self.pos].isdigit():
                self.pos += 1

        # Превращаем в число
        num_str = self.text[start:self.pos]
        if has_dot:
            return float(num_str)  # Дробное
        else:
            return int(num_str, base)  # Целое с нужной системой счисления

    def parse_value(self):  # Основной метод: разбирает любое значение — строка, число, список, словарь или структура
        self.skip_whitespace()  # Пропускаем пробелы.
        if self.pos >= len(self.text):  # Если текст кончился
            raise ValueError("Неожиданный конец файла")  # Ошибка
        ch = self.text[self.pos]  # Берём текущий символ
        if ch == '"':  # Если кавычка — строка
            return self.parse_string()
        elif ch.isdigit() or (ch == '-' and self.pos + 1 < len(self.text) and self.text[self.pos + 1].isdigit()):  # Если число или минус + число
            return self.parse_number()
        elif ch == '[':  # Если [ — список
            return self.parse_list()
        elif ch == '(':  # Если ( — кортеж
            return self.parse_tuple()
        elif ch == '{':  # Если { — словарь
            return self.parse_map()
        elif ch.isalpha() or ch == '_':  # Если буква или _ — может быть имя структуры или идентификатор
            saved_pos = self.pos  # Запоминаем позицию
            ident = self.parse_ident()  # Читаем имя
            self.skip_whitespace()  # Пропускаем пробелы
            if self.pos < len(self.text) and self.text[self.pos] == '(':  # Если после имени ( — это структура
                self.pos = saved_pos  # Возвращаемся назад
                return self.parse_struct()  # Парсим как структуру
            else:
                return ident  # Иначе просто имя (как константа)
        else:  # Неизвестный символ.
            raise ValueError(f"Неожиданный символ '{ch}' на позиции {self.pos}")
    def parse_list(self): #Метод для списка
        self.skip_whitespace()
        if self.pos >= len(self.text) or self.text[self.pos] != '[':
            raise ValueError(f"Ожидалась '[' на позиции {self.pos}")
        self.pos += 1
        items = []
        self.skip_whitespace()
        while self.pos < len(self.text) and self.text[self.pos] != ']':
            items.append(self.parse_value())
            self.skip_whitespace()
            if self.pos < len(self.text) and self.text[self.pos] == ',':
                self.pos += 1
                self.skip_whitespace()
        if self.pos >= len(self.text) or self.text[self.pos] != ']':
            raise ValueError(f"Ожидалась ']' на позиции {self.pos}")
        self.pos += 1
        return items

    def parse_tuple(self):  # Метод для кортежа
        self.skip_whitespace()
        if self.pos >= len(self.text) or self.text[self.pos] != '(':  # Должно начинаться с (
            raise ValueError(f"Ожидалась '(' на позиции {self.pos}")
        self.pos += 1 
        items = []  # Список для элементов кортежа
        self.skip_whitespace()
        while self.pos < len(self.text) and self.text[self.pos] != ')':  # Пока не )
            items.append(self.parse_value())  # Читаем значение.
            self.skip_whitespace()
            if self.pos < len(self.text) and self.text[self.pos] == ',':  # Если , — продолжаем
                self.pos += 1
                self.skip_whitespace()
        if self.pos >= len(self.text) or self.text[self.pos] != ')':  # Должно закрыться )
            raise ValueError(f"Ожидалась ')' на позиции {self.pos}")
        self.pos += 1 
        return tuple(items)  # Превращаем в кортеж и возвращаем

    def parse_map(self):  # Метод для словаря
        self.skip_whitespace()
        if self.pos >= len(self.text) or self.text[self.pos] != '{':  # Должно начинаться с {
            raise ValueError(f"Ожидалась '{{' на позиции {self.pos}")
        self.pos += 1
        map_ = {}  # Пустой словарь.
        self.skip_whitespace()
        while self.pos < len(self.text) and self.text[self.pos] != '}':  # Пока не }
            key = self.parse_string()  # Ключ — строка.
            self.skip_whitespace()
            if self.pos >= len(self.text) or self.text[self.pos] != ':':  # Должно быть : после ключа
                raise ValueError(f"Ожидалось ':' после ключа на позиции {self.pos}")
            self.pos += 1
            value = self.parse_value()  # Читаем значение
            map_[key] = value  # Добавляем в словарь
            self.skip_whitespace()
            if self.pos < len(self.text) and self.text[self.pos] == ',':  # Если , — продолжаем
                self.pos += 1
                self.skip_whitespace()
        if self.pos >= len(self.text) or self.text[self.pos] != '}':  # Должно закрыться }
            raise ValueError(f"Ожидалась '}}' на позиции {self.pos}")
        self.pos += 1
        return map_  # Возвращаем словарь

    def parse_struct(self):  # Метод для структуры
        ident = self.parse_ident()  # Читаем имя структуры
        self.skip_whitespace()
        if self.pos >= len(self.text) or self.text[self.pos] != '(':  # Должно быть ( после имени
            raise ValueError(f"Ожидалась '(' после '{ident}'")
        self.pos += 1
        fields = self.parse_fields()  # Читаем поля внутри
        self.skip_whitespace()
        if self.pos >= len(self.text) or self.text[self.pos] != ')':  # Должно закрыться )
            raise ValueError("Ожидалась ')'")
        self.pos += 1
        return {"struct": ident, "fields": fields}  # Возвращаем как словарь с именем и полями

    def parse_fields(self):  # Метод для чтения полей внутри структуры
        fields = {}  # Пустой словарь для полей
        while True:  # Цикл, пока есть поля
            self.skip_whitespace()
            if self.pos >= len(self.text) or self.text[self.pos] in ')}]':  # Если конец — выходим
                break
            key = self.parse_ident()  # Читаем имя поля
            self.skip_whitespace()
            if self.pos >= len(self.text) or self.text[self.pos] != ':':  # Должно быть : после имени
                raise ValueError(f"Ожидалось ':' после поля '{key}'")
            self.pos += 1
            value = self.parse_value()  # Читаем значение
            fields[key] = value  # Добавляем в словарь
            self.skip_whitespace()
            if self.pos < len(self.text) and self.text[self.pos] == ',':  # Если , — продолжаем
                self.pos += 1
                self.skip_whitespace()
        return fields  # Возвращаем словарь полей

    @staticmethod
    def parse(text): #Парсер  
        parser = RonParser(text) 
        return parser._do_parse()

    def _do_parse(self):  # Внутренний метод в parse
        self.skip_whitespace()
        result = self.parse_value()
        self.skip_whitespace()  #Пропускаем пробелы в конце
        if self.pos < len(self.text):  # Если после значения осталось что-то — ошибка
            raise ValueError(f"Лишние данные после конца на позиции {self.pos}")
        return result  # Возвращаем готовый объект

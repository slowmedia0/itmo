from ron_parser import RonParser

def escape_xml(s):  #Функция, которая убирает заменять спец символы, используемы в XML промежуточными для дальнейшей конвертации
    return str(s).replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;').replace('"', '&quot;').replace("'", '&apos;')

def to_xml(data, indent=0):  #Главная функция: строит XML-строку из данных. Рекурсивная — вызывает себя для вложенных частей.
    #indent — уровень отступа, чтобы XML был читаемым
    indent_str = "  " * indent  #Создаём строку из пробелов для отступа (2 пробела на уровень).
    lines = []  #Список конвертированных в XML строк
    #Если это словарь
    if isinstance(data, dict):
        #Проверяем, есть ли ключи "struct" и "fields" 
        if "struct" in data and "fields" in data:
            tag = data["struct"]  #Берём имя структуры как имя XML-тега.
            lines.append(f"{indent_str}<{tag}>")  #Добавляем открывающий тег с отступом.
            for key, value in data["fields"].items():  #Проходим по всем полям
                if isinstance(value, (list, tuple, dict)):  #Если это список, кортеж или словарь
                    lines.append(f"{indent_str}  <{key}>")  #Открываем вложенный тег для поля.
                    lines.extend(to_xml(value, indent + 2).split('\n'))  #Рекурсивно строим содержимое и добавляем строки.
                    #split('\n') разбивает результат на список строк, extend добавляет их все в список конвертированных строк.
                    lines.append(f"{indent_str}  </{key}>")  #Закрываем вложенный тег.
                else:  #Если значение простое (строка, число).
                    lines.append(f"{indent_str}  <{key}>{escape_xml(value)}</{key}>")  #Тег с значением внутри.
            lines.append(f"{indent_str}</{tag}>")  #Закрываем основной тег структуры.
        else:  #Простое значение (строка, число) 
            for key, value in data.items():  #Проходим по ключам и значениям.
                lines.append(f"{indent_str}<{key}>")  #Открываем тег.
                lines.extend(to_xml(value, indent + 1).split('\n'))  #Рекурсивно добавляем содержимое.
                lines.append(f"{indent_str}</{key}>")  #Закрываем тег.
    elif isinstance(data, (list, tuple)):  #Если данные — список или кортеж
        for item in data:
            lines.extend(to_xml(item, indent).split('\n'))  #Рекурсивно строим для каждого элемента YAML-строку и добавляем 
    else:  #Простое значение (число, строка — внутри списков).
        lines.append(f"{indent_str}<item>{escape_xml(data)}</item>")  #Оборачиваем в общий тег <item>.

    return "\n".join(line for line in lines if line.strip() != '')  #Соединяем строки в одну, убираем пустые.


with open('lab4dataRON.ron', 'r', encoding='utf-8') as f:  #Читаем RON-файл.
    ron_text = f.read()

    parsed = RonParser.parse(ron_text)#Парсер
    #Оборачиваем всё в один корневой тег <Root>, чтобы XML был валидным
    xml_content = '<?xml version="1.0" encoding="UTF-8"?>\n'  #Добавляем стандартный заголовок XML
    xml_content += '<Root>\n'
    xml_content += to_xml(parsed["fields"]["days"], indent=2)  #Строим XML-строку
    xml_content += '\n</Root>'
    with open('scheduleDOP3.xml', 'w', encoding='utf-8') as f:
        f.write(xml_content)

from ron_parser import RonParser

def ron_to_natural(data):  #Функция, которая убирает лишние "struct" и "fields", потому что в YAML пишут сразу названия
    #Если это словарь - проверяем на наличие ключей  "struct" и "fields"
    if isinstance(data, dict):
        #Проверяем, есть ли ключи "struct" и "fields" 
        if "struct" in data and "fields" in data:
            #Рассматриваем только содержимое fields и упрощаем его дальше
            return ron_to_natural(data["fields"])
        else:
        #Если обычный словарь — рекурсивно упрощаем все значения
            return {k: ron_to_natural(v) for k, v in data.items()}
    #Если список или кортеж — упрощаем каждый элемент
    elif isinstance(data, (list, tuple)):
        return [ron_to_natural(item) for item in data]
    else:
        #Простое значение (число, строка) — оставляем как есть
        return data

def yaml_escape(s):  #Функция, которая добавляет двойные кавычки и экранирует те, что уже есть внутри; перезаписывает пустые строки
    if s == "":  #Если пустая строка
        return "null"  #Пишем null, потому что в YAMK принято записывать строки через null
    s = str(s)
    #Добавляем двойные кавычки и экранируем те, что уже есть внутри
    return f'"{s.replace("'", '"')}"'

def to_yaml(data, indent=0):  #Конвертация бинарного файла в YAML
    spaces = "  " * indent  #Количество пробелов для отступа
    lines = []  #Список, куда будем добавлять строки после конвертации 

    #Если это словарь
    if isinstance(data, dict):
        for key, value in data.items():
            #Если значение — словарь или список, делаем блок
            if isinstance(value, (dict, list)):
                lines.append(f"{spaces}{key}:")  #Ключ и двоеточие
                lines.append(to_yaml(value, indent + 1))  #Рекурсивно добавляем содержимое после конвертации
            else:
                #Простое значение — ключ: значение
                lines.append(f"{spaces}{key}: {yaml_escape(value)}")
    
    #Если список или кортеж
    elif isinstance(data, (tuple, list)):
        for item in data:
            if isinstance(item, (tuple, list)):
                lines.append(f"{spaces}-")  #Минус на отдельной строке
                lines.append(to_yaml(item, indent + 1))  #Добавляем содержимое
            else:
                lines.append(f"{spaces}- {yaml_escape(item)}")  #Простой элемент
    
    else:
        #Простое значение (например, число внутри списка)
        lines.append(f"{spaces}{yaml_escape(data)}")

    #Соединяем все строки в одну с переносами
    return "\n".join(lines)

with open('lab4dataRON.ron', 'r', encoding='utf-8') as f:
    ron_text = f.read()

    parsed = RonParser.parse(ron_text)  #Парсер

    natural_data = ron_to_natural(parsed)  #Убираем лишние "struct" и "fields" 

    yaml_content = to_yaml(natural_data)  #Создаём YAML-строку
    
    with open('scheduleDOP1.yaml', 'w', encoding='utf-8') as f:
        f.write(yaml_content + "\n")


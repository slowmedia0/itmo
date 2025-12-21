import yaml
from ron_parser import RonParser

with open('lab4dataRON.ron', 'r', encoding='utf-8') as f:
    ron_text = f.read()

    parsed = RonParser.parse(ron_text)#Парсер

    yaml_content = yaml.dump(  #Вызываем метода dump, который превращает py-объект в YAML-строку.
        parsed,  #Что сериализовать: наш parsed объект.
        allow_unicode=True,  #Разрешаем русские символы без искажений.
        sort_keys=False,  #Не сортируем ключи — оставляем как в оригинале.
        indent=2,  #Отступы в 2 пробела для читаемости.
        default_flow_style=False,  #Списки выводим в "блок-стиле" — каждый элемент с новой строки и -.
        width=1000  #Максимальная ширина строки — чтобы не ломать длинные тексты.
    )
    with open('scheduleDOP2.yaml', 'w', encoding='utf-8') as f: 
        f.write(yaml_content)

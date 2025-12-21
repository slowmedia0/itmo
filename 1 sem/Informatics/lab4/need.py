from ron_parser import RonParser
with open('lab4dataRON.ron', 'r', encoding='utf-8') as f:  #Открываем файл для чтения
    ron_text = f.read()  #Читаем весь исходный файл-RON
    obj = RonParser.parse(ron_text)  #Парсер
    print("Десериализованный объект:")
    print(obj)

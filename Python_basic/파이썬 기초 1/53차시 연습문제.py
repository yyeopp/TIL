#다음의결과와같이'abcdef'문자열의각각의문자를키로하고0~5사이의정수를값으로하는딕셔너리객체를생성하고, 이딕셔너리객체의키와값정보를출력하는프로그램을작성하십시오.
#a: 0 b: 1 c: 2 d: 3 e: 4 f: 5

list = input("")
dict_list = dict(enumerate(list))
weneed = dict(map(reversed, dict_list.items()))
print(weneed.keys())
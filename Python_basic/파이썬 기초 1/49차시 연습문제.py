# 1~10 까지의정수를항목으로갖는리스트객체에서filter함수와람다식을이용해짝수만을선택해리스트를반환하는프로그램을작성하십시오.
# [2, 4, 6, 8, 10]

data = list(range(1, 11))
result = list(filter(lambda x: x%2==0, data))
print(result)
#1~10까지의정수를항목으로갖는리스트객체에서filter함수와람다식을이용해짝수만을선택한후, map함수와람다식을이용해항목의제곱값을갖는리스트를반환하는프로그램을작성하십시오.

olist = list(range(1,11))
even = list(filter(lambda x: x%2==0, olist))
result = list(map(lambda x: pow(x,2), even))
print(result)



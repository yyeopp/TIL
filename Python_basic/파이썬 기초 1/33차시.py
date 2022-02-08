# def calcsum(x,y):   # 함수 선언 위치가 중요
#     return x+y
# a,b = 2,3
# c = calcsum(a,b)
# print(c)

# def calc_sum(x,y,z):
#     result = x+y+z
#     return result
# res = calc_sum(1,2,3)
# print("calc_sum 함수 실행 결과는 %d"%res)

# def calc_sum(*n):       # 가변 매개변수를 명시적 매개변수와 혼합 가능하나, 가변매개변수를 마지막에 둬야 할 것
#     total = 0
#     for var in n:
#         total += var
#     return total
# result = calc_sum(2,3,4,1,5)
# print(result)

# def calc_sum(precision, *params):
#     if precision == 0:
#         total = 0
#     elif 0 < precision < 1:
#         total = 0.0
#     else:
#         total = 0
#     for n in params:
#         total += n
#     return total
# ask = calc_sum(0,55,6,8,45,3)
# qwe = calc_sum(0.6,5,5,5,8,4)
# zxc = calc_sum(57,8,4,45,6,854)
# print(ask, qwe,zxc)    *는 가변매개변수를 튜플 형식으로 전달, **는 딕셔너리 형식으로 전달..

# def ukau(**params):
#     for k in params.keys():       # 그냥 params 써도 되는데..?
#         print("{},{}".format(k,params[k]))
# ukau(a=1,b=2,c=3)

# def calc(x,y,operator="+"):   # operator 인자값은 기본이 +
#     if operator == "+":          # 기본값 설정하는 인자는 마지막에
#         return x+y
#     else:
#         return x-y
# ret=calc(10,5)
# print(ret)
# rwe=calc(10,6,"-")
# print(rwe)

# def calc(x,y):
#     result = x+y
#     print("아오")   # result 밑에서 같은 행에 있으면 출력됨
#     return result
# x = calc(1,2)
# print(x)

# 지역변수와 전역변수를 구별
# 지역변수는 바로 위에서 x,y 전역변수는 x 정도로 생각
# 혹시 둘이 이름이 같아버리면 헷갈리니까 조심
# 우선순위 상 함수는 본인 def 안에 들어있는 지역변수를 먼저 처리

# def cgv():
#     global x    # 전역변수 (x=5) 를 함수 내로 호출하는 구문
#     x +=1
# x=5
# cgv()
# print(x)        # cgv 함수로 x가 끌려들어간 이후이므로 6이 나옴

# def calc(opfn, x, y):   #중첩함수 설정. '함수'를 매개변수로 전달해 유연성을 높이는 방식
#     return opfn(x,y)       # 리턴으로 중복함수의 결과를 설정
# def plus(x,y):
#     return x+y
# def minus(x,y):
#     return x-y
# result = calc(plus, 10, 22)     # 외부함수 plus를 opfn 자리에 대입해서 calc를 실행시키는 구조조
# print(result)
#
# def calc(opfn, x, y):
#     return opfn(x,y)
# plus = calc(lambda a,b: a+b, 6,7)      # plus 함수를 새로 정의하는 대신 lambda a,b: a+b 로 opfn 자리에 직접 선언하는 방법
# print(plus)                                                 # 더 펀리하게 중첩함수 사용 가능

# def outerfn():
#     id = 0     #id는 지역변수에 해당
#     def innerfn():
#         nonlocal id        # nonlocal을 붙임으로써 innerfn 밖에 있는 지역변수인 id를 변경할 수 있음
#         id+=1                    # id를 innerfn 밖에 있는 outerfn에서 찾게함
#         return id
#     return innerfn         # () 안붙여야 함 (이해는 잘 안됨)
# makeid = outerfn()
# print(makeid())   #1
# print(makeid()) #2
# print(makeid())     #3

def input_d():
    radius_str = input("반지름을 입력하세요:")
    return float(radius_str)        # 입력된 반지름을 실수형으로 반환하는 장치
def calc_ca(r):
    return 3.14*r*r
def calc_cf(r):
    return 2*3.14*r
result = input_d()
# ca = calc_ca(result)          # 이걸 따로 선언해주는 것도 방법
# cf = calc_cf(result)
print("원의 면적: {0:.2f}, 원의 둘레: {1:.2f}".format(calc_ca(result), calc_cf(result)))
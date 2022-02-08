print(abs(-10))
a,b=9,5
print(divmod(a,b)) # 몫과 나머지를 튜플로

data = [1,2,3,4,5]
print(pow(data[2], 3))  # 리스트의 n번째 항복 3제곱
print(list(map(lambda x: pow(x, 3), data)))
# 람다는 무명의 함수를 선언하는 의미
# map은 map(함수, 반복작업 가능한 대상 - 리스트나 튜플 등)으로 작동
# map함수에 의해 대입된 데이터가 대입된 함수로 반복작업됨
# list에 의해 map 데이터를 list로 표현

list = [1,2,3]
print(all(list))
list = [1,2,"아"]
print(all(list))
list = [1,2,0]        # 하나라도 false 평가 시 false
print(all(list))        # False로 평가되는건 0, false, none 등
#
list = [10,20,0]
print(any(list))
list = [0,0,0]      # 모든 항목 false 시 false
print(any(list))    # 하나라도 True 평가 시 true

def iseven(num):              # 따로 함수 설정 안하고 람다로도 구현 가능
    return num%2 == 0
numbers = [1,2,3,4,5,6,7,8,9,10]
result = filter(iseven, numbers)  # 특정함수로 데이터를 필터링.
print(list(result))        # 결과물을 list 형태로 표현
#
data_str = "hello"
data_list = list(data_str)
print(data_list)
data_tuple = tuple(data_str)
print(data_tuple)
data_set = set(data_str)
print(data_set)
data_dict = dict(enumerate(data_str))   # enumerate는 데이터형 자료에 순서대로 인덱스를 부여해서
print(data_dict)                # 튜플 형태로 산출. 여기선 그 튜플을 dict에 집어넣은 것
# []는 list, ()는 tuple, {}는 set이거나 dict 인걸 잘 기억
# list, tuple, set에는 str 말고도 서로서로 넣을 수 있음
print(tuple(list(data_str)))
#
#
a = (10,20,8,3,10,91,51,321)
datalist = list(a)
print(datalist)
print(max(datalist)); print(min(datalist))
#
print(list(range(10)))
print(set(range(10)))
print(tuple(range(10)))
#
data = [1,8,5,153,15,5,461,22]
s_data = sorted(data) # 오름차순
r_data = list(reversed(s_data)) # 내림차순
rr_data = list(reversed(data))  # 그냥 순서만 뒤집는 (reversed함수의 기능)
print(s_data)    # 근데 list는 왜 따로 달아야하는걸까
print(r_data)
print(rr_data)
#
data1 = [1,2,3]         #묷는 데이터들은 모두 리스트 형태 + 개수 동일해야
data2 = [4,5,6]
data3 = ["a", "b","c"]
print(list(zip(data1,data2,data3)))  # 같은위치 원소들이 튜플로 묶임
print(dict(enumerate(zip(data1,data2,data3))))   # enumerate 활용해봄
print(dict(zip(data1, data3)))    # 리스트 두개를 합쳐서 dict 형태로 만드는 방법
#
#
a = 65
b = "가"
print(chr(a))
print(ord(b))
print(hex(a))

x = "10"
print(int(x, 2))      # 원래 2진법 str이라는 뜻
y = "3C"
print(int(y, 16))  # 원래 16진법 str이라는뜻
그 외에도 str, float 이미 써봄
#
data = "hello"
print(dir(data))
list = [1,5,1,651,651]
print(dir(list))      # dir이 뭐지 그래서?
#
x=1
def outerfn():      #globals는 전역변수,
    x=1             #locals는 지역변수의 상태?를 표현한다는데
    def innerfn():     #산출된 결과가 뭔소린지를 모르겠음
        x=1         # 일단 전역/지역이 뭔지나 잘 이해하자
a = globals()
print(locals())
print(a.keys())
print(a.values())
#
x = 10      # id 알아서 뭐하지
print(id(x))
print(hex(id(x)))   # 보통 16진수로 표현한다고
z = "10"
print(id(z))
print(hex(id(z)))
#
#
# class랑 인스턴스, 서브클래스를 안가르쳐놓고 다루네 시발
# 44차시 32분 근처는 다시듣기
#
#
print("hell".upper())  # .upper() 로 대문자만드는 함수를 소환?
expr = "2+5*3"
print(eval(expr))
vals = "'hell'.upper()"
print(vals)
print(eval(vals))
# eval은 str 안쪽에 있는 함수를 실행시키는듯...?
#
# data_list = list(range(1,21,1))
[1,2,3,4 ... 20]
# mapre = list(map(lambda x:x+5, data_list))
map함수에서 lambda를 많이 쓴다고
print(mapre)
filt = list(filter(lambda x:x%3==0, mapre))
# filter는 t/f 결과를 바탕으로 작동, list 형식을 넣어줘야함
print(filt)

map_str = input("항목 x에 대해 적용할 표현식 입력: ")
mapra = list(map(lambda x:eval(map_str), data_list))
# input을 이용해 스트링을 lambda 함수 안에 대입시키고, eval을 이용해 스트링 내의 계산식을 실행시킴
print(mapra)
fil_str = input("항목 x에 대해 필터링할 표현식 입력: ")
filta = list(filter(lambda x: eval(fil_str), data_list))
filter에 대해서도 똑같이 사용할 수 있음
print(filta)
# 자료 형태에 대한 이해가 중요할 것. str인지 list 인지
# map이 정확히 어떤건지.. 느낌은 오는데..


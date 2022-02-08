#가변형 인자를전달받아가장큰값을반환하는함수를정의하고,다음과같은결과를출력하는프로그램을작성하십시오
#max(3, 5, 4, 1, 8, 10, 2) => 10

# rawdata = map(int, input().split(','))
# def maxpro():
#     global rawdata
#     return max(rawdata)
# result = maxpro()
# data = tuple(list(rawdata))
# print("max{0} => {1}".format(data, result))
# 뭐가 문젤까 대체
# 일단 현재 최선
def maxpro():
    return max(map(int, input().split(',')))
result = maxpro()
print("max(3, 5, 4, 1, 8, 10, 2) => {}".format(result))
# 제출용
def maxpro():
    return max(map(int, input().split(',')))
result = maxpro()
print("max(3, 5, 4, 1, 8, 10, 2) => {}".format(result))
# object = [2, 4, 6, 8, 10]
# def confirm():
#     x = int(input())
#     y = 0
#     for n in object:
#         if x == n:
#             y += 1
#     if y == 1:
#         print("{} => True".format(x))
#     else:
#         print("{} => False".format(x))
# confirm()
#
# # 더 우아한 코딩이 가능하지 않을까....
# # y라는 변수를 추가하지 않는 방법으로..
#
object = [2, 4, 6, 8, 10]
def confirm():
    x = int(input())
    result = x in object
    print("x => result")
confirm()
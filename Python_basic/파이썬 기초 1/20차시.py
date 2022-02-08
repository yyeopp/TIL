# # # dan = int(input("단을 입력하세요: "))
# # #for i in (1,2,3,4,5,6,7,8,9):
# # #for i in range(1, 10, 1):
# # # for i in range(10):
# # #     print("{0} x {1} = {2:>2}".format(dan, i, dan*i))
# #
# # student = {1: "강성엽", 2: "임승민", 3:"김다현", 4: "강성윤"}
# # for key in student:
# #     print("{0}번 {1}".format(key, student[key]))
# #
# # future = "인생망한행떨"
# # for fuck in future:
# #     print("{0}".format(fuck))
# #
# # scores = [100, 51, 13, 18, 80]
# # total = 0
# # for score in scores:
# #     total += score
# # print(total)
#
# # dan = range(2,10,1)
# # i = range(1,10,1)
# # for x in dan:
# #     for y in i:
# #         print("{0} x {1} = {2:>2}".format(x,y,x*y))
# #         if y==9:
# #             print()
#
# dan = range(2,10,1)
# i = range(1,10,1)
# for x in dan:
#     for y in i:
#         print("{0} x {1} = {2:>2}".format(x,y,x*y))
#     print()   # 이건 공백 주는 용도, 안쪽 다 돌아가고 바깥 for 돌아가기 전에 실행
#
# scores = [80, 53, 98, 13, 53]
# count = len(scores)
# total = 0
# x = 0
# while x < len(scores):
#     total += scores[x]
#     x += 1
# print("총점은 {} 점".format(total))


# while True:
#     answer = input("명령을 입력하세요.\n'q'를 입력하면 프로그램이 종료됩니다. :")
#     if answer == "q":
#         break
#     print("'{}'를 입력하셨습니다.".format(answer))
# print("프로그램을 종료합니다...")

# numlist = range(1,21,1)
# total = 0
# for n in numlist:
#     if n % 3 == 0:
#         continue
#     total += n
# print(total)

# n = range(1,5,1)
# for i in n:
#     print("*" * i)
# for i in n:
#     print("*" * i)

# i = 1
# while i<5:
#     print("*" * i)
#     i +=1

# n = range(1,3,1)
# for k in n:
#     k = range(1, 5, 1)
#     for x in k:
#         print("*" * x)

# a=1; b=1
# while a<3:
#     while b<5:
#         print("*" * b)
#         b += 1
#     a +=1
#     b = 1

#      *
#     ***
#    *****
#   *******
#  *********
# ***********

a, b = 5, 1
while a >= 0 and b <= 11:
    print("{0}{1}".format(" "*a, "*"*b))
    a -= 1
    b += 2
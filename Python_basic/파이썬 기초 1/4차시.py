# # # # a = 5
# # # # print(type(a))
# # # # a = "강성엽"
# # # # print(type(a))
# # # #
# # # # var1 = 10
# # # # var2 = 10
# # # # var3 = 20
# # # # print(var1+var2==var3)
# # # #
# # # # student=("강성엽", "임승민", "김다현")
# # # # print(student[0])
# # # # print(student)
# # # #
# # # # student=["강성엽", "임승민", "김다현"]
# # # # print(student[1])
# # # # student[1]="박서진"
# # # # print(student[1])
# # #
# # # student={"강성엽", "임승민", "강성윤"}
# # # student|={"김다현", "박서진", "임승민"}
# # # print(student)
# #
# student={1:"강성엽", 2:"임승민", 3:"강성윤"}
# student[4]="김다현"
# print(student)
# del(student)
# print(student)
#
# obj={}
# print(obj is not None)
# #
# obj=None
# if obj:
#     print("obj는 None이 아님")
# else:
#     print("obj는 None")

from random import *
a=randint(1,10)
b=a*10+a
c=b*10+a
d=c*10+a
print(a+b+c+d)
# 다음은 10명의 학생들의 혈액형(A, B, AB, O) 데이터입니다.
# ['A', 'A', 'A', 'O', 'B', 'B', 'O', 'AB', 'AB', 'O']
# for 문을 이용하여 각 혈액형 별 학생수를 구하십시오.

#출력 {'A': 3, 'O': 3, 'B': 2, 'AB': 2}

# student = ['A', 'A', 'A', 'O', 'B', 'B', 'O', 'AB', 'AB', 'O']
# A,B,O,AB = 0,0,0,0
# for n in student:
#     if n == 'A':
#         A += 1
# for n in student:
#     if n == 'B':
#         B += 1
# for n in student:
#     if n == 'O':
#         O += 1
# for n in student:
#     if n == 'AB':
#         AB += 1
# print (A, B, O, AB)

student = ['A', 'A', 'A', 'O', 'B', 'B', 'O', 'AB', 'AB', 'O']
a,b,o,ab = 0,0,0,0
for btype in student:
    if(btype=='A'):
        a+=1
    if(btype=='B'):
        b+=1
    if(btype=='O'):
        o+=1
    if(btype=='AB'):
        ab+=1
print("{'A': %d, 'O': %d, 'B': %d, 'AB': %d}"%(a,o,b,ab))
#출력 {'A': 3, 'O': 3, 'B': 2, 'AB': 2}
# student = ['A', 'A', 'A', 'O', 'B', 'B', 'O', 'AB', 'AB', 'O']
# btype = ['A','B','O','AB']
# for k in btype:
#     for n in student:
#         if n == k:




# btype = ['A', 'B', 'O', 'AB']
# a,b,c,d = 0,0,0,0
# for n in range(1,5)
#     for k in student:
#         if k == 'A':
#             a += 1
# print(a)
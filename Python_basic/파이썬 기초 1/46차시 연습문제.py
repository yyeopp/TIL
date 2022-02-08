# "ADCBBBBCABBCBDACBDCAACDDDCAABABDBCBCBDBDBDDABBAAAAAAADADBDBCBDABADCADC"와
#
# 같은 문자열이 주어지고, A는 4점, B는 3점, C는 2점, D는 1점이라고 할 때 문자열에 사용된
#
# 알파벳 점수의 총합을 map 함수와 람다식을 이용해 구하십시오.      산출 184

data = list("ADCBBBBCABBCBDACBDCAACDDDCAABABDBCBCBDBDBDDABBAAAAAAADADBDBCBDABADCADC")
totalA = int(len(list(filter(lambda x: x=="A", data)))*4)
totalB = int(len(list(filter(lambda x: x=="B", data)))*3)
totalC = int(len(list(filter(lambda x: x=="C", data)))*2)
totalD = int(len(list(filter(lambda x: x=="D", data)))*1)
print(totalA+totalB+totalC+totalD)


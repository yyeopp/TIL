part1 = input("")
part2 = input("")
rsp1 = input("")
rsp2 = input("")

def rsp():
    if rsp1 == "가위" + rsp2 == "바위": print("{}가 이겼습니다".format(rsp2))
    if rsp1 == "가위" + rsp2 == "보": print("{}가 이겼습니다".format(rsp1))
    if rsp1 == "바위" + rsp2 == "가위": print("{}가 이겼습니다".format(rsp1))
    if rsp1 == "바위" + rsp2 == "보": print("{}가 이겼습니다".format(rsp2))
    if rsp1 == "보" + rsp2 == "바위": print("{}가 이겼습니다".format(rsp1))
    if rsp1 == "보" + rsp2 == "가위": print("{}가 이겼습니다".format(rsp2))
    if rsp1 == rsp2: print("비겼습니다")
# rsp(rsp1, rsp2)
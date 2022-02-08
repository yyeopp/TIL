# r=2 반지름 정보를 갖고, 원의 면적을 계산하는 메서드를 갖는 Circle 클래스를 정의하고, 생성한 객체의 원의 면적을 출력하는 프로그램을 작성하십시오.
# 원의 면적: 12.56
'''
class Circle:
    @staticmethod
    def calc(r):
        return (r**2) * 3.14
print(Circle.calc(2))
'''
class Circle:
    r=2
    def calc_area(self):
        return (self.r**2) * 3.14
newobject = Circle()
print("원의 면적: {}".format(newobject.calc_area()))


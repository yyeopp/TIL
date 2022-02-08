#가로, 세로정보을갖고, 사각형의면적을계산하는메서드를갖는Rectangle클래스를정의하고,생성한객체의사각형의면적을출력하는프로그램을작성하십시오
# 사각형의 면적: 20

class Rectangle:
    length = 4
    width = 5
    def calc_area(self):
        return self.length * self.width
newobject = Rectangle()
print("사각형의 면적: {}".format(newobject.calc_area()))
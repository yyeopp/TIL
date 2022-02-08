#Shape를부모클래스로Square자식클래스를정의하는코드를작성하십시오.Square클래스는length필드를가지며, 0을반환하는Shape클래스의area메서드를
#length * length값을반환하는메서드로오버라이딩합니다
# 정사각형의 면적: 9

class Shape:
    length = 3
    area = 0
class Square(Shape):
    area = Shape.length * Shape.length
newobject = Square()
print("정사각형의 면적: {}".format(newobject.area))
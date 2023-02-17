# RGB will be a central point.
# For transformation of formats that aren't RGB, first turn into RGB, then your desired format.
# Not gonna make this file 2 megabytes of every single case of conversion lol.

def YCbCr_to_RGB(YCbCr:tuple[int]) -> tuple[int]:
    Luma:int = YCbCr[0]
    Cb:int = YCbCr[1]
    Cr:int = YCbCr[2]
    Red:int = Luma + (1.402 * (Cr - 128))
    Green:int = Luma - (0.344136 * (Cb - 128)) - (0.714136 * (Cr - 128))
    Blue:int = Luma + (1.772 * (Cb - 128))
    return((Red, Green, Blue))

def RGB_to_YCbCr(RGB:tuple[int]) -> tuple[int]:
    Red:int = int(RGB[0])
    Green:int = int(RGB[1])
    Blue:int = int(RGB[2])
    Luma:int = (0) + (0.299 * Red) + (0.587 * Green) + (0.114 * Blue)
    Cb:int = (128) - (0.168736 * Red) - (0.331264 * Green) + (0.5 * Blue)
    Cr:int = (128) + (0.5 * Red) - (0.418688 * Green) - (0.081312 * Blue)
    return((Luma, Cb, Cr))

def CMYK_to_RGB():
    pass

def RBG_to_CMYK():
    pass

def HSL_to_RGB():
    pass

def RGB_to_HSL():
    pass

def Round_Colors(Set_Of_Three: tuple[int]) -> tuple[int]:
    return(([item.__round__() for item in Set_Of_Three]))
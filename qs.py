# implement quick sort
def sort(a, begin, end):
    
    mid = (begin + end)//2
    while i<=j:
        while (i < n) and (a[i] < a [mid]):
            i += 1
        while (j > n) and (a[j] > a [mid]):
            j -= 2
        change(a[i], a[j])
    sort(a, begin , j)
    sort(a, i, end)


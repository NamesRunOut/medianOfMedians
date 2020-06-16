package com.company;

public class medianOfMedians {
    static void swap(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    static void insertSort(int[] a, int beg, int end){
        for (int i=beg; i<end; ++i) {
            int key=a[i];
            int j=i-1;
            while (j>=beg && a[j]>key) {
                a[j+1] = a[j];
                j = j-1;
            }
            a[j+1] = key;
        }
    }
    static int partition(int[] a, int l, int r){
        // partition, Lomuto version
        int pivot = a[r];
        int i = (l-1);
        for (int j=l; j<r; j++) {
            if (a[j]<=pivot) {
                i++;
                swap(a, i ,j);
            }
        }
        swap(a, i+1, r);
        return i+1;
    }
    static int check(int[] a, int l, int r, int elem){
        // in-place median of medians algorithm
        // a[] - array
        // l - element "from"
        // r - element "to"
        // elem - kth element we're looking for
        if (r-l<=5){
            insertSort(a, l, r+1);
            return l+elem-1;
        } else{
            int count=l;
            for (int i=l;i<r;i=i+5){
                if (i+5<r) {
                    insertSort(a, i, i+5);
                    swap(a, count, i+2);
                    // swapping the found medians with elements
                    // at the beginning of the considered array (from "l")
                    // instead of creating a new array to store medians
                }
                else {
                    insertSort(a, i, r);
                    swap(a, count, (i+r)/2);
                }
                count++;
            }
            int medianaMedian = check(a, l, count-1, (count-l+1)/2);
            swap(a, medianaMedian, r);
            int pivot = partition(a, l, r);
            int elem2 = pivot-l+1;
            if (elem==elem2) return pivot;
            else if (elem<elem2) return check(a, l, pivot-1, elem);
            else return check(a, pivot+1, r, elem-elem2);
        }
    }
    public static void main(String[] args) {
        // assumption: query is >=0 and <arr.length
        // elements dont need to be distinct
        int[] a = {1,5,4,6,7,4,5,2,3,4,3,4,3,2,3,4,5,6,7,8,14};
        int query = 20;
        System.out.println(a[check(a,0,a.length-1,query)]);
        query = 5;
        System.out.println(a[check(a,0,a.length-1,query)]);
    }
}

#include "C:\CPP\lab2\lab2.h"
#include <stdio.h>

void question1(double vector1[], double vector2[],int n)
{
    double* vector3 = malloc(n*sizeof(double));
    printf("vector1 is:\t");
    print_vector(vector1, n);
    printf("vector2 is:\t");
    print_vector(vector2, n);

    add_vectors( vector1,  vector2,  vector3,  n);
    printf("vector1 + vector2:\t");
    print_vector(vector3,n);

    double product = scalar_prod( vector1,  vector2,  n);
    printf("scalar product of v1 and v2: %f\n", product);

    double norm = norm2( vector1,  vector1, n);
    printf("norm2 is: %f\n", norm);
    printf("\n-----------end of question1------------\n");
}

void question2(double mat[][N])
{
    printf("The matrix: \n");
    for (int i=0; i<N;i++)
        print_vector((mat[i]),N);
    if (is_diag_dom(mat)) printf("is diagonally dominant");
    else printf("is not diagonally dominant");

    printf("\n-----------end of question2------------\n");
}

void question3(int arr[],int size)
{
    diagonal_print(arr,size);
    printf("\n-----------end of question3------------\n");
}

void question4(const char word[], int freq[26])
{
    letter_freq(word,freq);
    printf("The word frequency is : \n");

    for(int i=0; i<26; i++){
        printf("The count of '%c' is %d\n",65+i,freq[i]);
    }


    printf("\n-----------end of question4------------\n");
}

void question5(const char source[], char destination[], int n)
{
    string_copy(source, destination, n);
    printf("The source string:\n");
    print(source);
    printf("The copy string:\n");
    print(destination);
    printf("\n-----------end of question5------------\n");


}

void question6(const int source[], int val[], int pos[], int size, int m)
{

    efficient(source, val, pos, size);
    print_vector2(source,size);
    print_vector2(val,m);
    print_vector2(pos,m);

    int *new_scr = malloc(sizeof(int) * size);
    reconstruct(new_scr, m,val, pos, size);
    print_vector2(new_scr,size);

    printf("\n-----------end of question6------------\n");
}

void question7(int val1[],int val2[],int pos1[], int pos2[],int val1_size,int val2_size)
{



    int* val3 = malloc(sizeof(int)*(val1_size+val2_size));
    int* pos3 = malloc(sizeof(int)*(val1_size+val2_size));

	addEff(val1, val2, val3, pos1, pos2, pos3, val1_size, val2_size);
    for (int i = 0; i < val1_size+val2_size; i++)
		printf("\npos:%d, val:%d", pos3[i],val3[i]);

}

int main()
{
    double vector1[] = {4,5,6.4};
    double vector2[] = {3,4,5};
    int n = sizeof(vector1)/sizeof(double);

    double mat[][N] = {1,1,1,1,1,2,3,4,1,3,6,10,1,4,10,20};

    int square_mat[] = {1,12,13,49,5,16,17,81,9,10,11,20,2,45,19,14};

    int freq[26] = {0};
    char destination[8] ={0};
    char word[] = "this is a test\0";


    int src[] = {3,0,23,0,-7,0,0,48};
    int src_len = sizeof(src)/sizeof(int);


    question1(vector1,vector2, n);
    question2(mat);
    question3(square_mat,N*N);
    question4(word,freq);
    question5(word,destination,8);


    int s = val_size(src, src_len);
    int* val = malloc(sizeof(int)*s);
    int* pos = malloc(sizeof(int)*s);
    question6(src,val,pos,src_len,s);


    int val1[] = {1,4,7};
    int val2[] ={2,4,8};
    int pos1[] = {1,2,3};
    int pos2[] = {2,4,5};
    int val1_size = sizeof(val1)/sizeof(int);
    int val2_size = sizeof(val2)/sizeof(int);
    question7(val1,val2,pos1, pos2,val1_size,val2_size);


}

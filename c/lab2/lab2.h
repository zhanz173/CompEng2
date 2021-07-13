#ifndef LAB2_H_INCLUDED
#define LAB2_H_INCLUDED
#define  N  4

void print_vector(double arr[], int siz);
void print_vector2(int arr[], int siz);
void add_vectors(double vector1[], double vector2[], double vector3[], int n);
double scalar_prod(double vector1[], double vector2[], int n);
double norm2(double vector1[], double vector2[], int n);
void diagonal_print(int arr[],int size);
int is_diag_dom(int mat[][N]);
void letter_freq(const char word[], int freq[26]);
void string_copy(const char source[], char destination[], int n);
void efficient(const int source[], int val[], int pos[], int size);
void reconstruct(int source[], int m, const int val[], const int pos[], int n);
void addEff(int val1[], int val2[], int val3[], int pos1[], int pos2[], int pos3[], int k1, int k2);

int const str_size(const char str[]);
void const print(const char str[]);
int val_size(int src[],int len);
#endif // LAB2_H_INCLUDED

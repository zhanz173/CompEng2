#ifndef LAB3_H_INCLUDED
#define LAB3_H_INCLUDED
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

typedef struct{
    int id,proj1_grad,proj2_grad;
    float final_grad;
    char first_name[15],second_name[15];
}student;
char* get_input();
void print_str(const char* str) ;
char* my_strcat(const char* const str1, const char* const str2);
student** create_class_list(char* filename, int* sizePtr );
int find(int idNo, student** list, int size);
int find_student(char* name,student** list, int size);
void input_grades(char *filename, student **list, int size);
void compute_final_course_grades(student** list, int size);
void print_list(student **list, int size);
int name_match(char* const str1,char* const str2);
void output_words(char** list, int *nPtr);
void withdraw(int idNO, student **list, int *sizePtr);
void destory_list(student **list, int *sizePtr);
char** read_words(const char* input_filename, int *nPtr);
void sort_words(char** list, int *nPtr);
void sort_id(int* id_buffer, int* num);
void sort2_words(char** list, int *nPtr);
void output_words(char** list, int *nPtr);

void test1(student** list, int* sizePtr);
void test2();

#endif // LAB3_H_INCLUDED

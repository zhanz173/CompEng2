#include "lab3.h"

void test0()
{
    char *str1, *str2, *strcat;
    str1 = get_input();
    str2 = get_input();
    strcat = my_strcat(str1,str2);
    print_str(strcat);

    free(str1);
    free(str2);
    free(strcat);
}

/*
test function withdraw 1 failure 2 success
*/
void test1(student** list, int* sizePtr)
{

    int id;
    printf("Enter the student id:\n");
    scanf("%d",&id);
    withdraw(id,list,sizePtr);
    print_list(list, *sizePtr);
}

void test2()
{
    int nPtr = 0;
    char* input_filename = "c://cpp/lab3/words.txt";
    char** list = read_words(input_filename, &nPtr);
    sort2_words(list, &nPtr);
    output_words(list, &nPtr);
    printf("-----------\n");
}


void test4()
{
	int nPtr = 0;
	char input_filename[] = "c://cpp/lab3/words.txt";
	printf("%s\n", input_filename);
	char** list = read_words(input_filename, &nPtr);

    output_words(list,&nPtr);
    sort2_words(list,&nPtr);
    printf("\n---------After sorting---------\n");
    output_words(list,&nPtr);
}

int main()
{

    char* students = "c://cpp/lab3/students.txt";
    char* grades = "c://cpp/lab3/grades.txt";
    int i = count_students(students);

    student** p = create_class_list(students, &i);
    input_grades(grades, p, i);
    compute_final_course_grades(p,3);
    //test0();
/*
    printf("\n--------Withdraw function--------\n");
    test1(p,&i);
    test1(p,&i);
    test1(p,&i);

    printf("\n---------Output grades---------\n");
    test3(p,i);
    */
    test4();
    return 0;
}

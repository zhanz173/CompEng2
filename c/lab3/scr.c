#include "lab3.h"
#define ERROR 1
char* get_input()
{
    char* string = (char*)malloc(128);     //preallocate memory as string buffer
    char* ptr = string;
    char ch;

    printf("Enter a string, 0 to stop (Max 127 characters)\n");
    do{
        scanf("%c",&ch);
        if(ch == '\n')continue;
        *ptr = ch;
        ptr++;
        if((ptr - string) >= 127)   //buffer overflow
        {
            printf("Too many characters\n");
            break;
        }
    }while(ch != '0');

    *(--ptr) = 0;
    //flush out any inputs after detect 0
    fflush(stdin);
    return string;
}
/*Get input names, return an array of char array(string)*/
char** get_names(int* num)
{
    int status = 0;
    char c = 0;
    printf("How many students?\n");
    if(scanf("%d",num)==0)
        {
            printf("invalid input\n");
            exit(1);
        }

    char** p = (char**)malloc((*num)*sizeof(char*));  //Get input from user and allocate memory
    for(int i =0; i<*num; i++)
    {
        p[i] = calloc(1,30);
        if(!p[i])
        {
            status = ERROR;
        }
    }
    if(status != ERROR)
    {
        for(int i=0, j=0; i<*num; i++,j=0)
        {
            printf("\nEnter the name for student %d: \n",i+1);
            c = getchar();
            while (c == '\n')   c = getchar(); //Get rid of all newline characters

            do
            {
                p[i][j] = c;
                j++;
                c = getchar();
            }while(c != '\n');
            printf("You entered: %s\n",p[i]);
        }
    }
    else   //Error handling
    {
         for(int i =0; i<*num; i++)
        {
            free(p[i]);
        }
        free(p);
        printf("function: get_names: Not enough memory\n");
        exit(1);
    }
    return p;
}

void print_str(const char* str)
{
    do{
        printf("%c",*str);
        str++;
    }while(*str != 0);
    printf("\n");
}

char* my_strcat(const char * const str1, const char * const str2)
{
    size_t len = strlen(str1)+strlen(str2);
    char* ptr = (char*)realloc((void*)str1,len+1); //copy str1 to new address and leave space for str2 and a null character
    char* strcat = ptr;
    ptr += strlen(str1);

    for (int i = 0; i<strlen(str2); i++)
        *ptr++ = str2[i];
    *ptr = 0;
    return strcat;
}

//return the number of student in the file
int count_students(char* filename)
{
    FILE *input_file = fopen(filename,"r");
    char c;
    int count = 1;
    if (!input_file)
    {
        printf("Cannot open the specified file\n");
        exit(1);
    }
    for (c = getc(input_file); c != EOF; c = getc(input_file))
        if (c == '\n')              // Increment count if this character is newline
            count = count + 1;
    fclose(input_file);
    return count;
}

student** create_class_list(char* filename, int* sizePtr)
{
    int status = 0;
    FILE *input_file = fopen(filename,"r");

    if (!input_file)
    {
        printf("Cannot open the specified file\n");
        exit(1);
    }

    student** student_ptr = (student**)calloc((*sizePtr),sizeof(student*));    //allocate memory for student pointers
    if (!student_ptr)       //check if allocation is successful
    {
        status = ERROR;
    }
    else
    {
        for (int i=0; i<*sizePtr; i++)
        {
            student_ptr[i] = (student*)calloc(1,sizeof(student));

            if (!student_ptr[i])            // check if student_ptr is successfully allocated
            {
                status = ERROR;
                break;
            }
            fscanf(input_file,"%d%s%s",&(student_ptr[i]->id),student_ptr[i]->first_name,student_ptr[i]->second_name);
        }
        if (status == ERROR)
        {
            for(int i=0; i<*sizePtr; i++)
                free(student_ptr[i]);
        }
    }
    if (status == ERROR)        //free all the allocated memory and terminate the program
    {
        free(student_ptr);
        printf("Not enough memory!\n");
        fclose(input_file);
        exit(1);
    }

    fclose(input_file);
    return student_ptr;
}

int find(int idNo, student** list, int size)
{
    for (int i=0; i<size; i++)
    {
        if(list[i]->id == idNo)
            return i;
    }
    return -1;
}

void input_grades(char *filename, student **list, int size)
{
    FILE *input_file = fopen(filename,"r");
    int id;
    int proj1_grad;
    int proj2_grad;
    int index = -1;


    if (!input_file)
    {
        printf("Cannot open the specified file\n");
        exit(1);
    }

    for (int i=0; i<size; i++)
    {
        if(fscanf(input_file,"%d%d%d\n",&id,&proj1_grad,&proj2_grad) != 3)  //Error checking
        {
            printf("File: %s format isn't supported\n",filename);       // Format error
            exit(1);
        }

        index = find(id,list,size);//return corresponding index of student list, -1 if not found
        if (index != -1)
        {
            list[index]->proj1_grad = proj1_grad;
            list[index]->proj2_grad = proj2_grad;
        }
    }
    fclose(input_file);
}

void compute_final_course_grades(student** list,int size)
{
    for(int i=0; i<size; i++)
    {
        list[i]->final_grad = (list[i]->proj1_grad + list[i]->proj2_grad)/2;
    }
}

void output_final_course_grades(student** list, int size)
{
    FILE* output = fopen("students_grades.txt","w");
    int* num = (int*)malloc(sizeof(int));
    compute_final_course_grades(list,size);
    char** names = get_names(num);

    //buffer to store index for sorting
    int* index_buffer = (int*)malloc((*num)*sizeof(int));

    //validate input name and adjust buffer size
    {
        int i,j;    //j = number of students found in list
        for (i=0,j=0; i<*num; i++)
        {
            int index = find_student(names[i], list, size);
            if(index != -1)
            {
                index_buffer[j] = index;
                j++;
            }
            else
            {
                printf("student %s not found\n",names[i]);
            }
            free(names[i]);     //names[i] is manually allocated memory, free every time after used
        }

        // sorting the index before output to a file
        sort_id(index_buffer,&j);
        fprintf(output,"%d\n",j);
        for (int i=0; i<j; i++)
        {
            printf("%d %f\n",list[index_buffer[i]]->id,list[index_buffer[i]]->final_grad);
            fprintf(output,"%d %f\n",list[index_buffer[i]]->id,list[index_buffer[i]]->final_grad);
        }
    }

    free(num);
    free(index_buffer);
    free(names);
    fclose(output);
}

/*  insertion sort
    sorting the index array into increasing order
    implementation details see bonus question
*/
void sort_id(int* index_buffer, int* num)
{
    int i,j,id;
    for(i=0; i<*num; i++)
    {
        id = index_buffer[i];
        j = i-1;
        while(j>=0 && index_buffer[j]>id)
        {
            index_buffer[j+1] = index_buffer[j];
            j--;
        }
        index_buffer[j+1] = id;
    }
}


void print_list(student **list, int size)
{
    for (int i=0; i<size; i++)
        printf("Id: %d, name: %s %s, project 1 grade: %d, project 2 grade: %d, final grade: %.2f\n",
               list[i]->id,list[i]->first_name,list[i]->second_name,list[i]->proj1_grad,
               list[i]->proj2_grad,list[i]->final_grad);
}

void withdraw(int idNO, student **list, int *sizePtr)
{
    int index = find(idNO, list, *sizePtr);
    if(index == -1)     //check if index is legal
    {
        printf("Student not found\n");
        return;
    }
    free(list[index]);  //free the memory if student is removed

    int i = index;
    student** temp1;
    student** temp2;
    while(i < *sizePtr-1)   //Move elements after list[i] one position forwards
    {
        temp1 = &list[i];
        i++;
        temp2 = &list[i];
        *temp1 = *temp2;
    }
    *sizePtr -= 1;  //decrement the size of the array
    list[*sizePtr] = NULL;
}

void destory_list(student **list, int *sizePtr)
{
    for(int i =0; i<*sizePtr; i++)
    {
        free(list[i]);
    }
    free(list);
    list = NULL;
    *sizePtr = 0;
}

char** read_words(const char* input_filename, int *nPtr)
{
    char buffer[128];
    size_t len = 0;
    FILE* input_file = fopen(input_filename,"r");

    if (!input_file)
    {
        printf("Cannot open the specified file\n");
        exit(1);
    }
    fscanf(input_file,"%d\n",nPtr);

    char** list = (char**)calloc(*nPtr,sizeof(char*));
    for(int i =0; i<*nPtr; i++)
    {
        //Get all character in one line, end with newline
        fgets ( buffer, 128, input_file);
        len = strlen(buffer);
        char* str_ptr = (char*)malloc(len+1); //Allocate one more space for null character
        strncpy(str_ptr,buffer,len);    //copy buffer string to new address
        str_ptr[len] =0;
        list[i] = str_ptr;  //Add string pointer to array
    }
    fclose(input_file);
    return list;
}

/* Function to sort an array using insertion sort*/
void sort_words(char** list, int *nPtr)
{
    char* word;
    int i,j;

    for(i=1; i<*nPtr; i++)
    {
        word = list[i];
        j = i - 1;

        /* Move elements of list[j...n] 1 position back if they are greater than list[i]. Then
         insert list[i] to beginning*/
        while (j >= 0 && list[j][0] > word[0])
        {
            list[j + 1] = list[j];
            j--;
        }
        list[j+1] = word;
    }
}

/* Function to sort an array using bubble sort*/
void sort2_words(char** list, int *nPtr)
{
    int i,j;
    int swapped;
    char* temp;

    /*  Compare 2 adjacent words alphabetically and swap if the first word is larger.
        Break if there is no word swapped*/
    for(i=0; i<*nPtr-1; i++)
    {
        swapped = 0;
        for(j=0;j<*nPtr-i-1;j++)
        {
            if(list[j][0] > list[j+1][0])
            {
                temp = list[j];
                list[j] = list[j+1];
                list[j+1] = temp;
                swapped = 1;
            }
        }
        /*Break if no word gets swapped */
        if (!swapped)   break;
    }
}

void output_words(char** list, int *nPtr)
{
    for (int i=0; i<*nPtr; i++)
    {
        printf("%s",list[i]);
    }
    printf("\n");
}


/* function used for testing only */
int count_char(FILE* input_file)
{
    int c;
    int count = 0;

    while((c=fgetc(input_file)) != EOF)
    {
        count++;
    }
    return count;
}
/* Find students by the name */
int find_student(char* name,student** list ,int size)
{
    /* Allocate memory to store user input*/
    char first_name[15] = {0};
    char second_name[15] = {0};

    {
    int i = 0;
    int j = 0;
    int add_fname = 1;
        while(name[i])
        {
            if(name[i] == ' ')      //detect space to switch to second name
            {
                i++;
                add_fname = 0;
                continue;
            }

            if(add_fname)
            {
                first_name[i] = name[i];

            }
            else
            {
                second_name[j] = name[i];
                j++;
            }
            i++;
        }
    }

    for(int i=0; i<size; i++)
    {
        if(name_match(first_name,list[i]->first_name))
            if(name_match(second_name,list[i]->second_name))
                return i;
    }
    return -1;
}


int name_match(char* const str1,char* const str2) //compare two string, return 1 if equal(case insensitive)
{
    int i = 0;
    if (strlen(str1) == strlen(str2))
    {
        while (str1[i])
        {
            if(toupper(str1[i]) != toupper(str2[i]))
                return 0;
            i++;
        }
        return 1;
    }
    return 0;
}

#include "lab1.h"


void question1()
{
    const int MIN_MULTIPLE = 32;             //The smallest MIN_MULTIPLE of 4 above 30 is 32
    int Sum = 0;
    for(int i = MIN_MULTIPLE;i<=1000; i+=4){Sum +=i;}

    printf("%d \n",Sum);

    int i = MIN_MULTIPLE;
    Sum = 0;
    while (i <= 1000)
    {
        Sum += i;
        i += 4;
    }
    printf("%d \n",Sum);

    i = MIN_MULTIPLE;
    Sum = 0;
    do{
        Sum += i;
        i += 4;
    }while(i <=1000);
    printf("%d \n",Sum);

}

void question2()
{
    int num = 0;
    int Min = INT_MAX;      //initialize the smallest number to max integer
    int val = 0;

	printf("How many integers are in your input? \n");
	do {
		scanf("%d", &num);
	} while (num <= 0);

    for (int i=0; i<num; i++)
    {
        printf("Enter the %dth number \n", i+1);
        scanf("%d", &val);
        Min = Min > val ? val:Min;
    }
    printf("The smallest number is %d\n", Min);
}

void question3()
{
	int num = 0;
	do {
		printf("Please input an integer(0 to end)\n");
		scanf("%d", &num);
		if (abs(num) / 10000 >=1 && abs(num) / 10000 <10)       //check if input is a 5 digit number
		{
			printf("Your input is a five-digit number: %d\n", num);
		}
	} while (num != 0);
}

void question4()
{
    float Avg = 0;
    printf("Please enter student's average\n");
    scanf("%f",&Avg);

    if (Avg>=0 && Avg <=100)
    {
        if (Avg >=90) printf("4\n");
        else if (Avg >=80 && Avg <90) printf("3\n");
        else if (Avg >=70 && Avg <80) printf("2\n");
        else if (Avg >=60 && Avg <70) printf("1\n");
        else printf("0\n");
    }
    else printf("Invalid input\n");
}

void question5()
{
    const float INIT_VAL = 4;
    float ans = 0;
    int terms = 1;
    int i = 1;

    do {
        printf("How many terms do your need: \t");
        scanf("%d", &terms);
    }while (terms < 0);

    for (;i<=terms; i++)
    {
        if (i%2 == 1) ans+=INIT_VAL/(2*i-1);
        else ans-=INIT_VAL/(2*i-1);
    }
    printf("pi = %f\n",ans);
}


void question6()
{
	const int Max_val = 400;
	const int Max_side =(int) Max_val / sqrt(2);            //We stop for loop when side1 = side2
	float side1 = 1; float side2 = 1; float hypotenuse = 1;
	for (int i = 0; i < Max_val; i++)       //check every hypotenuse from 1 to 400
	{
		for (int j = 0; j < i; j++)
		{
			side2 = sqrtf((hypotenuse + side1) * (hypotenuse - side1));             //a^2 = (c+b)*(c-b)
			if (side2 < side1 || side1 > Max_side) break;           // break when side1 = side2
			if (side2 == (int)side2) printf("side1: %d, side2: %d, hypotenuse: %d\n",
				(int)side1, (int)side2, (int)hypotenuse);      // print only when both side and hypotenuse are integers
			side1++;
		}
		side1 = 1;          //reset side1 after checked all possible (side1,hypotenuse) value and increment hypotenuse
		hypotenuse++;
	}
}

void question7()
{
	int m = 0;
	printf("Enter an integer m: \n");
	scanf("%d", &m);
	printf("The following numbers are perfect:\n");
	for (int i = 1; i <= m; i++)
	{
		int sum = 0;
		for (int j = 1; j <= (sqrt(i)); j++)
		{
			if (i % j == 0)        //True when j is a factor of i
			{
				if (i / j == i)     //if i/j = i only add j to sum
					sum += j;
				else
					sum += i/j + j;     // (i/j) * j = i, so both are factors of j
			}
		}
		if (sum == i) printf("%d\n", i);
	}
}

void question8_v1()
{
    int num = 0;
    scanf("%d",&num);
    printf("the inverse of the number is: \n");
    do{
        if (num%10 != 0) printf("%d",num%10);
        num /= 10;
    } while(num >= 1);
}

void question8_v2()
{
	char* ptr = NULL;
	ptr = (char*)calloc('b',1024);
	int size = 0;
	int leading_zero = 0;

	for (int i = 0; i < 1024  ; i++)
	{
		*ptr = getchar();
		if (*ptr == '\n') break;
		ptr++;
		size++;
	}

    ptr--;
    if (*ptr == '0') leading_zero = 1;

	for (int i = 0; i < size  ; i++)
    {
        if(leading_zero && *ptr=='0')
        {
            ptr--;
            continue;
        }

        leading_zero = 0;
        printf("%c",*ptr);
        ptr--;
    }
    free(ptr+1);
}


void question9()
{
    char ch;
    char current_char = '0';
    int count = 0;

    do{
        ch = getchar();
        if (current_char == ch) count++;
        else
        {
            printf("%d",count);
            current_char = ch;
            count = 1;
        }
    }while(ch != '\n');

}

#include <stdio.h>
#include <stdlib.h>
void question7(int val1[], int val2[], int pos1[], int pos2[], int val1_size, int val2_size);

struct array{
    int* arr;
    int length;
};

int addEff(int val1[], int val2[], int val3[], int pos1[], int pos2[], int pos3[], int k1, int k2)
{
	int i = 0, j = 0, repeat = 0;			// i is the subscript for pos1,  j is the subscript for pos2

	for (int k = 0; k < k1 + k2; k++)
	{
		if (i >= k1)			//add the entire array pos2 to pos3 if done looping through pos1
		{
			pos3[k] = pos2[j];
			val3[k] = val2[j];
			j++;
			continue;
		}

		else if (j >= k2)
		{
			pos3[k] = pos1[i];
			val3[k] = val1[i];
			i++;
			continue;
		}

		else if (pos1[i] > pos2[j])
		{
			pos3[k] = pos2[j];
			val3[k] = val2[j];
			j++;
		}

		else if (pos1[i] < pos2[j])
		{
			pos3[k] = pos1[i];
			val3[k] = val1[i];
			i++;
		}

		else					//discard both pos1[i] and pos2[j] if they are equal
		{
			pos3[k] = pos2[j];
			val3[k] = val2[j] + val1[i];
			i++;
			j++;
			repeat++;
		}

	}
	return repeat;
}

void question7(int val1[], int val2[], int pos1[], int pos2[], int val1_size, int val2_size)
{



	int* val3 = (int*)malloc(sizeof(int) * (val1_size + val2_size));
	int* pos3 = (int*)malloc(sizeof(int) * (val1_size + val2_size));

	int repeat = addEff(val1, val2, val3, pos1, pos2, pos3, val1_size, val2_size);
	for (int i = 0; i < val1_size + val2_size-repeat; i++)
		printf("\n(pos:%d, val:%d)", pos3[i], val3[i]);

}

struct array get_input(void)
{
	printf("Enter the values, 0 to stop:\n");
	int* buffer = (int*)malloc(sizeof(int) * 1024);
	int* temp = buffer;
	int counter = 0;
	int val = 0;
	do
	{
		scanf("%d", &val);
		*buffer = val;
		buffer++;
		counter++;
	} while (val != 0);
	counter--;
	buffer = temp;
	int* ptr = (int*)malloc(sizeof(int) * counter);

	for (int i = 0; i < counter; i++)
	{
		*ptr = *temp;
		ptr++;
		temp++;
	}
	free(buffer);
	struct array arr = { (ptr - counter),counter };
	return arr;
}

void print(struct array* vector)
{
    for(int i=0; i< vector->length; i++)
    {
        printf("%d \t", vector->arr[i]);
    }
}
int main()
{
    printf("Please enter val1: \n");
	struct array val1 = get_input();
	printf("val1: ");
	print(&val1);

	printf("\nPlease enter Val2:\n");
	struct array val2 = get_input();
    printf("val2: ");
	print(&val2);

	printf("\nPlease enter pos1:\n");
	struct array pos1 = get_input();
    printf("pos1: ");
	print(&pos1);

	printf("\nPlease enter pos2:\n");
	struct array pos2 = get_input();
    printf("pos2: ");
	print(&pos2);

	if (pos1.length != val1.length) printf("inappropriate length");
	if (pos2.length != val2.length) printf("inappropriate length");

    question7(val1.arr, val2.arr, pos1.arr, pos2.arr, val1.length, val2.length);

}

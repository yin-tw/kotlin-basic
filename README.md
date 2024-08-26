## ColumnSequenceUtil

This utility can be used to convert column numbers to column indices (as used in Excel or Google Sheets) and which are represented as: A, B, C, ..., Z, AA, AB, AC, ..., ZZ, AAA
It consists of two public functions
- numberSequenceToColumnIndexLabels
- numberToColumnIndices

### numberSequenceToColumnIndexLabels

This function allows you to convert number sequence to column index labels.

The function takes two parameters ( both greater than zero) : 
    - starting sequence number and 
    - count of results (i.e. number of labels you need)

Function returns 
    - a string array representing the sequence of alphabetical column labels starting from the given starting sequence number.

Example:
Parameters (1, 1) should return [A].
Parameters (1, 2) should return [A, B].
Parameters (26, 3) should return [Z, AA, AB].

### numberToColumnIndices

This function allows you to convert a number to column indices
The function takes a number and returns a string

Example:
Parameter 1 should return A
Parameter 18278 should return ZZZ 

### limitations
The utility will throw exception 
- for any input beyond 18278 and 
- for sequence labels that go beyond ZZZ when ending sequence numbers are beyond 18278


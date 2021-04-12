#include <bits/stdc++.h>

using namespace std;

typedef long long int           ll;
typedef unsigned long long int ull;
typedef long double             ld;

#define f(i, a, b)              for(int (i) = (a); (i) < (b); ++(i))
#define all(a)                  (a).begin(), (a).end()
#define deb(x)                   cout << #x << ": " << x << endl;

int main()
{
    ll processSize = 0, pageSize = 0, mainSize = 0;
    cout << "Enter the Size of a process (in KB): ";
    cin >> processSize;
    cout << "Enter the Page Size (in bytes): ";
    cin >> pageSize;
    cout << "Enter the Size of the Physical Memory (in MB): ";
    cin >> mainSize;

    ll totalFrames = (mainSize * pow(2, 20)) / pageSize;
    ll numberOfPages = (processSize * pow(2, 10)) / pageSize;

    ld bitsOfPageSize = log2(pageSize);
    ld bitsOfTotalFrames = log2(totalFrames);
    ld bitsOfNumberOfPages = log2(numberOfPages);

    cout << "\n1. Total number of frames in main memory: " << totalFrames << endl;
    cout << "2. Total number of entries in page table: " << numberOfPages << endl;

    cout << "3. Number of bits in Physical Address: " << bitsOfPageSize + bitsOfTotalFrames << " bits" << endl;
    cout << "4. Number of bits in Logical Address: " << bitsOfPageSize + bitsOfNumberOfPages << " bits" << endl;

    ll sizeOfPageTable = 0;
    cout << "\nEnter the number of entries in the Page Table: ";
    cin >> sizeOfPageTable;

    map<ll, ll> pageTable;

    f(i, 0, sizeOfPageTable) {
        cout << endl;
        ll pageNumber, frameNumber;
        cout << "Enter Page Number: ";
        cin >> pageNumber;
        cout << "Enter Frame Number: ";
        cin >> frameNumber;

        pageTable[pageNumber] = frameNumber;
    }

    cout << "Page No.  Frame No.  Valid Bit" << endl;

    f(i, 0, numberOfPages) {
        // Page No.
        cout << i << " \t ";

        // Frame No. & Valid Bit
        cout << pageTable[i] << " \t " << ((pageTable[i]) ? "1" : "0") << endl;
    }

    cout << endl;
    bool ok = 1;
    while(ok) {
        string logicalAddress;
        cout << "\nEnter " << bitsOfPageSize + bitsOfNumberOfPages << " bits of Logical Address: ";
        cin >> logicalAddress;
        reverse(all(logicalAddress));
        ll number = 0, j = 0;
        f(i, 0, logicalAddress.length()) {
            number += (logicalAddress[i] - '0') * pow(2, j++);
        }
        deb(number)
        cout << ((pageTable[number]) ? "PAGE HIT" : "PAGE MISS") << endl;
        cout << "\nContinue? [0 / 1]: ";
        cin >> ok;
    }

    return 0;
}

capacity = int(input('ENTER NUMBER OF PAGE FRAMES : '))
processList = [int(x) for x in input('ENTER REFERENCE STRING (COMMA SEPERATED) : ').split(',')]
                  
# List of current pages in Main Memory
s = [] 
miss, hits = 0, 0
status = ''

for i in processList:
  
    # If i is not present in currentPages list
    if i not in s:
  
        # Check if the list can hold equal pages
        if(len(s) == capacity):
            s.remove(s[0])
            s.append(i)
            status = 'Miss'
        else:
            s.append(i)
            status = 'Miss'
  
        # Increment Page faults
        miss +=1
  
    # If page is already there in 
    # currentPages i.e in Main
    else:
          
        # Remove previous index of current page
        s.remove(i)
  
        # Now append it, at last index
        s.append(i)
        status = 'Hit'
    
    print(s, end = " ")
    print('\tPage', status)

hits = len(processList) - miss
print()
print(f'TOTAL NUMBER OF HITS : {hits}')
print(f'TOTAL NUMBER OF MISS : {miss}')
print(f'HIT RATIO : {(hits/len(processList)):.2f}')
print(f'MISS RATIO : {(1 - hits/len(processList)):.2f}')

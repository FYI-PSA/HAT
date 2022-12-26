# General algorithm for a word list generator

## How it works

1. Get a list of words from input 

   * Ensure that there's a mechanism for stopping, and that it won't accidentally stop with a normal input (Don't use a simple "done")
2. Get its file name from the user

   * Also get the file path
3. Specify chain length with the help of user

   1. Get minimum length for chain
   2. Get maximum length for chain

   * It should include both ends  ⇨  `Start = S` , `End = E`  →  `[S, E]`
4. Create a writer function

   * It should take a string, write it and then append a newline
     * ! Get the file name from section 2 of this page !
5. Create the word list itself

   * Here's the algorithm explained in a pseudocode format:

     * ``` txt
       String  BaseString = ""
       Int     ChainLength = NUMBER
       List    WORDLIST = {'Word1', 'Word2', 'Another thing', ...}


       Function CreateWordList(OriginalString, LengthVariable):

           * This flag will only run once in every recursion cycle
           * It keeps the chain length the same on every loop of a recursion
           IndexFlag = FALSE

           for each WORD in the WORDLIST list:

               NewString = OriginalString + WORD

               * It checks if the IndexFlag is FALSE and wont run if it's TRUE
               if it's the first WORD in this index:
                   Reduce LengthVariable by 1
                   Set IndexFlag to TRUE for the rest of the loop

               * If we still haven't reached the end a.k.a. the max chain length
               if the LengthVariable is above 0:
               Call this function again, with the modified string and length:
                   CreateWordList(NewString,ReducedLengthVariable)
               else:
                   *A unique chain with the length of ChainLength is created
                   WriteToFileFunction(NewString)
       ```

   * Here's what it looks like simplified and efficient using python:

     * ```python
        base = ""
        chainLen = 3
        #note the words have have dashes after them to make
        #the output more readable
        wordList = ['hello-', 'WORLD-']

        def Create(x, k):
            flag_bool = False
            for f in wordList:
                z = x + f
                if not flag_bool:
                    k = k - 1
                    flag_bool = True
                if k > 0:
                    Create(z, k)
                else:
                    WriteFunction(z)

        Create(base, chainLen)
       ```

     * Assuming our `WriteFunction` word properly, this code creates the following:
       * `'hello-hello-hello-'`
       * `'hello-hello-WORLD-'`
       * `'hello-WORLD-hello-'`
       * `'hello-WORLD-WORLD-'`
       * `'WORLD-hello-hello-'`
       * `'WORLD-hello-WORLD-'`
       * `'WORLD-WORLD-hello-'`
       * `'WORLD-WORLD-WORLD-'`
     * ! Get WriterFunction from section 4 of this page !
6. The program needs to call this function multiple times with varying lengths for the chain length variable, to be more efficient and require the user less work.

   * Create a loop that goes from and includes both inputs from **Section 3's Minimum and Maximum chain length inputs**
7. Done! Now to make the UX more smooth and likeable by adding some finishing touches and bug fixes, includnig:

   * If another file with the same name exists on the same path, ask the user if they want to:
     * **V**iew it, **A**ppend to the end of it, or completely **O**verride the previous file
     * ! set the default to be Append mode !
   * Default the chain length range to a small and useful one to be efficient and also fast
     * A good one is **`Start=2`, `End=6`**
   * Make the terminal input and output look nice and pleasing
     * Even go as far as to make this a GUI app!

### That covers everything

#### Enjoy this guide on how the algorithm for this app works :)

##### ~Funtime-UwU

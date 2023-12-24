Runtime Results (for finding anagrams of least in words.txt file):

Data Structure   |   Runtime 1   |   Runtime 2   |   Runtime 3   |   Runtime 4   |   Runtime 5   
-------------------------------------------------------------------------------------------
Hash             |   232         |   235         |   251         |   236         |   237
Avl              |   525         |   513         |   513         |   517         |   524
Bst              |   1150        |   1166        |   1157        |   1163        |   1147
(time in milliseconds)


Answers to Questions:
I expected the hash table to generally have the best performance. I expected the bst to have the slowest runtime. The timing of my program’s execution matched my expectations. 

With hashtables, inputting n items into a hashtable results in a total runtime of around θ(n). Retrieving the desired key then takes, on average, only θ(1) time. In the worst case, where there is separate chaining and some fraction of n elements happen to be in one very large chain, it would take around O(n). Thus, the runtime of finding the anagrams of a word, assuming that anagrams are present, results in a runtime that is some multiple of n, which results in a total runtime of θ(n+1), simplifying to θ(n) (even in the worst case, n ends up being a larger multiple of n, such as 2n, but still has a magnitude of n).

With avl trees, inputting a set of n values into the tree takes around θ(n lg n) time. Searching for the desired key in the avl tree is around θ(lg n). So the avl tree ends up having an overall runtime of θ(n lg n +  lg n), which simplifies to θ(n lg n), which ends up being worse than the hash table's runtime, which matched what I anticipated.

For the bst, its runtimes are relatively similar to the avl tree, except that bst’s don’t have to maintain a balancing property as avl trees do. This can result in runtimes being as high as O(n^2) for inserting n items into a bst. Though the average runtimes are the same as avl trees, the probability of having very long tree branches is significant, which results in more frequent cases of the worst case runtime, which is O(n^2 + n), simplifying to O(n^2), for inserting n items into the tree and then searching for the desired key/item in the tree. So keeping in mind that the probability of having worst-case runtimes is quite high due to the lack of a balancing condition in the bst, which makes avl tree runtimes more advantageous on average, the bst ends up having the worst-case runtime more frequently, which is worse than the runtimes for the other two data structures, also lining up with my expectations.

***Note: For all the runtimes, I didn’t mention the time complexity of insertion sort, as given the same word searching for the same anagrams in the different data structures, the runtime of the insertion sort is (ideally) the same for all three data structures.

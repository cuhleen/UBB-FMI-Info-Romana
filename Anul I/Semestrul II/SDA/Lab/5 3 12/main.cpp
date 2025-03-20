#include <iostream>

int main()
{

    //int *a = (int*)malloc(sizeof(int) * 100000);
    //free(a);
    //a = NULL;

    // Același lucru cu

    int *a = new int[10000];
    delete [] a;
    a = nullptr;

    //------------------------------------------------------------------------------------------------

    /*
     *      Pseudocod pentru o funcție de căutare într-o stivă
     * Funcție caută(TElem elem, Stiva s)
     *   {pre: s este o stivă, elem este un element}
     *   {post: s rămâne neschimbată}
     *
     *   Pentru i = 0, s.size, 1:
     *     Dacă s.elements[i] == elem:
     *       caută <- true
     *     sf.Dacă
     *   sf.Pentru
     *   
     *   caută <- false
     *
     * sf.Funcție
     */

    return 0;
}

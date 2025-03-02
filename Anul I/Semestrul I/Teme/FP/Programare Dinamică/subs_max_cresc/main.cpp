/**
 Presupunem n maxim 10000
*/
#include <cstdio>
using namespace std;

int a[10001],t[10001],l[10001],rez[100001];

int main()
{
    int n,k,i,lmax,imax;
    FILE *f=fopen("sir.in","r");
    fscanf(f,"%d",&n);
    for(i=1; i<=n; i++)fscanf(f,"%d",&a[i]);
    printf("Sirul citit:\n");
    for(i=1; i<=n; i++)printf("%d ",a[i]);
    printf("\n");
    for(k=1; k<=n; k++)
    {
        lmax=imax=0;
        for(i=1; i<k; i++)
            if(a[k]>a[i]&&l[i]>lmax)
            {lmax=l[i];imax=i;}
        l[i]=lmax+1;
        t[i]=imax;
    }
/** for(i=1;i<=n;i++)
        printf("%d ",t[i]);
    printf("\n");
    for(i=1;i<=n;i++)
        printf("%d ",l[i]);   */
    lmax=imax=0;
    for(i=1;i<=n;i++)if(l[i]>lmax){lmax=l[i];imax=i;}
    printf("Cel mai lung subsir crescator are lungimea %d\n",lmax);
    i=imax;
    while(i)
    {
        rez[l[i]]=a[i];
        i=t[i];
    }
    for(i=1;i<=lmax;i++)
        printf("%d ",rez[i]);
    return 0;
}

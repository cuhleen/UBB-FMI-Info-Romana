#include <iostream>
#include "Film.h"
#include "repo.h"
#include "teste.h"
#include "consola.h"

void rulareMain() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{ repository, validator, cosFilme };
    UI ui{ service, cosFilme};
    ui.ruleaza();
    
}

int main()
{
    ruleazaToateTestele();
    rulareMain();

    return 0;
}
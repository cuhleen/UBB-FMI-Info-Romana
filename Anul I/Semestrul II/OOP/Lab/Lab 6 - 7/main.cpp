#include "Repo/FilmRepo.h"
#include "UI/console.h"
#include "Tests/tests.h"

void rulareMain() {
    Repo repository;
    Validator validator;
    Service service{ repository, validator };
    UI ui{ service };
    ui.ruleaza();

}

int main()
{

    ruleazaToateTestele();
    rulareMain();

    return 0;
}
#include "Tests.h"
#include "FilmRepo.h"
#include "Service.h"

void Tests::runAllTests() {
	testRepo();
	testService();
	testValidation();
}
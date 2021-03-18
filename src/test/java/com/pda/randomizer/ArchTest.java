package com.pda.randomizer;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.pda.randomizer");

        noClasses()
            .that()
            .resideInAnyPackage("com.pda.randomizer.service..")
            .or()
            .resideInAnyPackage("com.pda.randomizer.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.pda.randomizer.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}

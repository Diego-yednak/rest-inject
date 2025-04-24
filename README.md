# rest-inject – REST client injection made simple for Spring

[![License](https://img.shields.io/github/license/Diego-yednak/rest-inject)](LICENSE)
[![Build](https://img.shields.io/github/actions/workflow/status/Diego-yednak/rest-inject/build.yml)](https://github.com/Diego-yednak/rest-inject/actions)

**rest-inject** is a lightweight Java library for automatically injecting REST clients based on OpenAPI interface definitions.  
It simplifies integration testing in Spring applications by removing boilerplate code and reducing setup complexity.

---

## ✨ Features

- ✅ Automatic REST client injection from OpenAPI interfaces
- ⚙️ Compatible with Spring and Spring Boot
- 🧪 Simplifies integration tests with real HTTP calls
- 🔧 Easily configurable and extendable

---

## 🚀 Getting Started

### 1. Add as a Maven dependency

```xml
<dependency>
    <groupId>io.github.diegoyednak</groupId>
    <artifactId>rest-inject</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
## 📦 Usage

```java
class PetApiTest {

    @InjectRestClient PetApi petClient;

    @Test
    void successfullyAddsPet() {
        Pet pet = new Pet();
        pet.setName("Dog");
        // Act
        ResponseEntity<Pet> responsePet = this.petClient.addPet(pet);
        Pet petSaved = responsePet.getBody();
        // Assert
        Assertions.assertNotNull(petSaved);
        Assertions.assertNotNull(petSaved.getId());
        Assertions.assertEquals("Dog", petSaved.getName());
    }

    @Test
    void failsToAddPetWithDuplicateName() {
        Pet pet = new Pet();
        pet.setName("Cat");
        // Act
        this.petClient.addPet(pet);
        
        HttpErrorResponseException exception = Assertions.assertThrows(HttpErrorResponseException.class,
                () -> this.petClient.addPet(pet)
        );
        Error error = exception.getBody();
        // Assert
        Assertions.assertNotNull(error);
        Assertions.assertEquals("Pet with name Cat already exists", error.getMessage());
    }

}
```

## 🎯 Example OpenAPI Interface

```java
public interface PetApi {
    @Operation(
            operationId = "addPet",
            summary = "Add a new pet to the store.",
            description = "Add a new pet to the store.",
            tags = {"pet"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = Pet.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "422", description = "Validation exception"),
                    @ApiResponse(responseCode = "default", description = "Unexpected error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = Error.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "petstore_auth", scopes = {"write:pets", "read:pets"})
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/pet",
            produces = {"application/json", "application/xml"},
            consumes = {"application/json", "application/xml", "application/x-www-form-urlencoded"}
    )

    default ResponseEntity<Pet> addPet(
            @Parameter(name = "Pet", description = "Create a new pet in the store", required = true) @Valid @RequestBody Pet pet
    ) {
        // ...
    }
}

```

## 🤔 Why rest-inject?

Traditional integration tests in Spring often require extensive configuration and setup for REST clients.  
**rest-inject** removes this burden by automatically injecting clients from OpenAPI specs, allowing you to:

- Write fewer lines of code
- Focus on business logic
- Quickly validate external APIs

## 🙌 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

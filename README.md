# Java Web Project SP-2 "pokedex"

### Description

Data can potentially be worth a lot of money. A common use case is to collect, store, and enrich related data from various sources (API’s, webpages etc) - and then expose the data in useful places. Sometimes new and interesting products can emerge from this.

In this exercise we will train our data collecting skills using JPA, DTOs, Java streams, and fetching data from REST API’s.

### Overview

#### API

pokemon.json:
```
{
  "id": 33,
  "name": "nidorino",
  "base_experience": 128,
  "height": 9,
  "is_default": true,
  "order_number": 61,
  "weight": 195,
  "location_area_encounters": "https://pokeapi.co/api/v2/pokemon/33/encounters",
  "hp": 61,
  "attack": 72,
  "defense": 57,
  "speed": 65,
  "specie_name": "nidorino",
  "specie_url": "https://pokeapi.co/api/v2/pokemon-species/33/",
  "sprite": {
    "id": 381,
    "back_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/33.png",
    "back_female": null,
    "back_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/33.png",
    "back_shiny_female": null,
    "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/33.png",
    "front_female": null,
    "front_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/33.png",
    "front_shiny_female": null
  },
  "types": [
    {
      "name": "poison",
      "url": "https://pokeapi.co/api/v2/type/4/"
    }
  ],
  "abilities": [
    {
      "name": "hustle",
      "url": "https://pokeapi.co/api/v2/ability/55/"
    },
  ],
  "forms": [
    {
      "name": "nidorino",
      "url": "https://pokeapi.co/api/v2/pokemon-form/33/"
    }
  ],
  "moves": [
    {
      "name": "amnesia",
      "url": "https://pokeapi.co/api/v2/move/133/"
    },
    ]
```

#### Entities

![uml](https://github.com/Oskar123456/sp-2-pokedex/blob/master/report/ERD.png?raw=true)

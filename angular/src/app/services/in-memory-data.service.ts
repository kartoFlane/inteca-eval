import { InMemoryDbService } from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const child = [
    	{
    		"id": 1,
    		"firstName": "Paweł",
    		"secondName": "Nowak",
    		"pesel": "12312312312",
    		"sex": "M",
    		"birthDate": "2000-01-01",
    		"familyId": 1
    	},
    	{
    	  "id": 2,
    	  "firstName": "Ala",
    	  "secondName": "Nowak",
    	  "pesel": "34534534534",
    	  "sex": "F",
    	  "birthDate": "2001-01-01",
    	  "familyId": 1
    	}
    ];
    const father = [
    	{
    		"id": 1,
    		"firstName": "Jan",
    		"secondName": "Kowalski",
    		"pesel": "23423423423",
    		"birthDate": "1980-01-01",
    		"familyId": 1
    	}
    ];
    const family = [
    	{
    		"id": 1,
    		"father": father[0],
    		"children": [child[0], child[1]]
    	}
    ];

    return { family, child, father };
  }
}


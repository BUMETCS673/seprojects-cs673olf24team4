# TeamBuilder API Documentation

## Base URL
http://localhost:8085/api

## Docker URL
http://localhost:8085/api

## Admin Credentials
Username: admin
Password: password123

## User Object

Example User object:
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "name": "First Last",
  "email": "f.d@example.com",
  "role": "Developer",
  "answers": ["Java", "Spring", "Team Player"],
  "groupId": 1
}
```

## User Management

### Get All Users

Endpoint: GET /users

Description: Retrieves all users in the system.

Request Parameters:
- adminUsername: string (query parameter)
- adminPassword: string (query parameter)

Response: Array of User objects

### Get User by ID

Endpoint: GET /users/{userId}

Description: Retrieves a specific user by their ID.

Response: User object


### Create User

Endpoint: POST /users

Description: Creates a new user.

Request Body:
```json
 {
    "name": "New User",
    "email": "newuser@example.com",
    "role": "Designer",
    "answers": ["Collaborator", "Visual communication"]
  }
```
- Response: Created User object

### Update User

Endpoint: PUT /users/{userId}

Description: Updates an existing user.

Request Parameters:
- adminUsername: string (query parameter)
- adminPassword: string (query parameter)

Request Body: Same as Create User

Response: Updated User object

### Delete User

Endpoint: DELETE /users/{userId}

Description: Deletes a user from the system.

Request Parameters:
- adminUsername: string (query parameter)
- adminPassword: string (query parameter)

Response: 204 No Content

## Group Management

### Get All Groups

Endpoint: GET /groups

Description: Retrieves all groups in the system.

Response: Array of Group objects

### Create Group

Endpoint: POST /groups

Description: Creates a new group.

Request Body:
```json
 {
    "name": "New Group"
  }
```
- Response: Created Group object

### Assign User to Group
Endpoint: POST /users/{userId}/assign-group/{groupId}

Description: Assigns a user to a specific group.

Response: Updated User object

### Get Users in Group

Endpoint: GET /users/group/{groupId}

Description: Retrieves all users assigned to a specific group.

Response: Array of User objects

## Team Assignment

### Assign Teams within a Group

Endpoint: POST /groups/{groupId}/assign-teams

Description: Assigns users in a group to teams. Requires admin authentication.

Request Parameters:
- numberOfTeams: int (query parameter)
- adminUsername: string (query parameter)
- adminPassword: string (query parameter)

Example Request:
```
POST /api/groups/1/assign-teams?numberOfTeams=3&adminUsername=admin&adminPassword=password123
```

Response: 
- Success: Array of Team objects
- Unauthorized: 401 status code with message "Admin authentication failed"

### Get Team Assignments for Group

Endpoint: GET /groups/{groupId}/teams

Description: Retrieves the current team assignments for a group.

Response: Same as Assign Teams within a Group response

## User Answers

### Submit User Answers

Endpoint: POST /users/{userId}/submit-answers

Description: Submits or updates a user's answers.

Request Body:
```json
  {
    "answers": ["Leader", "Work under pressure", "Frontend development"]
  }
```
- Response: Updated User object

### Get User Answers

Endpoint: GET /users/{userId}/answers

Description: Retrieves a user's submitted answers.

Response: Array of strings (answers)

## Error Handling

All endpoints may return the following error responses:

400 Bad Request: Invalid input data

404 Not Found: Requested resource not found

500 Internal Server Error: Unexpected server error

## Team Management

### Get Teams by Group ID

Endpoint: GET /groups/{groupId}/teams

Description: Retrieves all teams assigned to a specific group.

Response: Array of Team objects

```json
{
  "id"
  "teamNumber"
  "group"
  "members"
}
```
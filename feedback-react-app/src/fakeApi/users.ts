import Mock from "fakeApi/mock";
import jwt from "jsonwebtoken";

const JWT_SECRET = "jwt_secret_key";
const JWT_VALIDITY = "1 days";

const userList = [
  {
    id: 1,
    name: "User Teste",
    username: "user_teste",
    email: "teste@teste.com",
    avatar: "/static/avatar/001-man.svg",
    age: 25,
    // password: 'pipoca1234'
  },
];

//Login
Mock.onPost("/api/auth/login").reply(async (config) => {
  try {
    await new Promise((resolve) => setTimeout(resolve, 1000));

    const { email } = JSON.parse(config.data);
    const user = userList.find((user) => user.email === email);
    console.log(user);
    if (!user) {
      return [400, { message: "Email ou senha invalida" }];
    }
    const accessToken = jwt.sign({ userId: user.id }, JWT_SECRET, {
      expiresIn: JWT_VALIDITY,
    });
    return [
      200,
      {
        accessToken,
        user: {
          id: user.id,
          avatar: user.avatar,
          email: user.email,
          name: user.name,
        },
      },
    ];
  } catch (error) {
    console.error(error);
    return [500, { message: "Internal server error" }];
  }
});

//Create new user
Mock.onPost("/api/auth/register").reply(async (config) => {
  try {
    await new Promise((resolve) => setTimeout(resolve, 1000));

    const { name, email, username } = JSON.parse(config.data);
    const user = userList.find((user) => user.email === email);
    if (user) {
      return [400, { message: "Usuário já existe!" }];
    }

    const newUser = {
      id: 2,
      name: name,
      username: username,
      email: email,
      avatar: "/static/avatar/001-man.svg",
      age: 25,
    };
    userList.push(newUser);

    const accessToken = jwt.sign({ userId: newUser.id }, JWT_SECRET, {
      expiresIn: JWT_VALIDITY,
    });

    return [
      200,
      {
        accessToken,
        user: {
          id: newUser.id,
          avatar: newUser.avatar,
          email: newUser.email,
          name: newUser.name,
          username: newUser.username,
        },
      },
    ];
  } catch (error) {
    console.error(error);
    return [500, { message: "Internal server error" }];
  }
});

Mock.onGet("/api/auth/profile").reply((config) => {
  try {
    //@ts-ignore
    const { Authorization } = config.headers;
    if (!Authorization) {
      return [401, { message: "Invalid Authorization token" }];
    }
    const accessToken = Authorization.split(" ")[1];
    const { userId }: any = jwt.verify(accessToken, JWT_SECRET);
    const user = userList.find((u) => u.id === userId);

    if (!user) {
      return [401, { message: "Invalid authorization token" }];
    }

    return [
      200,
      {
        user: {
          id: user.id,
          avatar: user.avatar,
          email: user.email,
          name: user.name,
        },
      },
    ];
  } catch (err) {
    console.error(err);
    return [500, { message: "Internal server error" }];
  }
});

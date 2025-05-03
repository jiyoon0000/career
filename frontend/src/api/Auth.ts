import axios from 'axios';

interface LoginRequest {
  email: string;
  password: string;
}

export async function login({ email, password }: LoginRequest) {
  try {
    const response = await axios.post(`${process.env.EXPO_PUBLIC_API_BASE_URL}/api/auth/login`, {
      email,
      password,
    });
    return response.data; // 보통 accessToken 반환
  } catch (error: any) {
    console.error('로그인 실패:', error.response?.data || error.message);
    throw error;
  }
}

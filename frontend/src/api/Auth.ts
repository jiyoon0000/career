import axios from 'axios';

interface LoginRequest {
  email: string;
  password: string;
}

interface EmailSendRequest {
  email: string;
}

interface EmailVerifyRequest {
  email: string;
  code: string;
}

const API = process.env.EXPO_PUBLIC_API_BASE_URL;

export async function sendVerificationCode({ email }: EmailSendRequest) {
  try {
    const response = await axios.post(`${API}/api/auth/email/send`, { email });
    console.log('이메일 전송 응답:', response.data);
    return response.data;
  } catch (error: any) {
    console.error('이메일 전송 실패:', error.response?.data || error.message);
    throw error;
  }
}

export async function verifyEmailCode({ email, code }: EmailVerifyRequest) {
  try {
    const response = await axios.post(`${API}/api/auth/email/verify`, { email, code });
    return response.data;
  } catch (error: any) {
    console.error('이메일 인증 실패:', error.response?.data || error.message);
    throw error;
  }
}

export async function signup({ email, password }: { email: string; password: string }) {
  try {
    const response = await axios.post(`${API}/api/auth/signup`, {
      email,
      password,
    });
    return response.data;
  } catch (error: any) {
    console.error('회원가입 실패:', error.response?.data || error.message);
    throw error;
  }
}


export async function login({ email, password }: LoginRequest) {
  try {
    const response = await axios.post(`${API}/api/auth/login`, { email, password });
    return response.data.data;
  } catch (error: any) {
    console.error('로그인 실패:', error.response?.data || error.message);
    throw error;
  }
}

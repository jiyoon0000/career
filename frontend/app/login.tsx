import React, { useState } from 'react';
import { router } from 'expo-router';
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  ScrollView,
  StyleSheet,
  Alert,
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { login } from '@/api/Auth';
import axios from 'axios';

const API = process.env.EXPO_PUBLIC_API_BASE_URL;

export default function LoginScreen() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [autoLoginChecked, setAutoLoginChecked] = useState(false);

  const handleLogin = async () => {
    try {
      const res = await login({ email, password });
      const accessToken = res.accessToken;
      await AsyncStorage.setItem('accessToken', accessToken);

      if (autoLoginChecked) {
        await AsyncStorage.setItem('autoLogin', 'true');
      }

      const onboardingRes = await axios.get(`${API}/api/onboarding/completed`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      const isCompleted = onboardingRes.data.data.completed;

      if (isCompleted) {
        router.replace('/(tabs)/studyroom');
      } else {
        router.replace('/onboarding/StartScreen');
      }
    } catch (error) {
      Alert.alert('로그인 실패', '이메일 또는 비밀번호가 올바르지 않습니다.');
    }
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <ScrollView style={styles.scroll}>
        <View style={styles.header}>
          <Text style={styles.title}>로그인</Text>
        </View>

        <View style={styles.logoWrapper}>
          <Image source={require('@/assets/images/logo.png')} style={styles.logo} />
        </View>

        <View style={styles.form}>
          <Text style={styles.label}>이메일</Text>
          <View style={styles.inputWrapper}>
            <TextInput
              placeholder="이메일 주소를 입력해 주세요"
              value={email}
              onChangeText={setEmail}
              style={styles.textInput}
              autoCapitalize="none"
              keyboardType="email-address"
            />
            {email.length > 0 && (
              <TouchableOpacity onPress={() => setEmail('')}>
                <Image
                  source={require('@/assets/images/right-icon-wrapper.png')}
                  style={styles.iconButton}
                />
              </TouchableOpacity>
            )}
          </View>

          <Text style={[styles.label, styles.passwordLabel]}>비밀번호</Text>
          <View style={styles.inputWrapper}>
            <TextInput
              placeholder="비밀번호를 입력해 주세요"
              value={password}
              onChangeText={setPassword}
              style={styles.textInput}
              secureTextEntry={!showPassword}
            />
            {password.length > 0 ? (
              <>
                <TouchableOpacity onPress={() => setShowPassword(!showPassword)} style={{ marginRight: 1 }}>
                  <Image
                    source={
                      showPassword
                        ? require('@/assets/images/input-field-icon.png')
                        : require('@/assets/images/right-icon-wrapper-2.png')
                    }
                    style={styles.iconButton}
                  />
                </TouchableOpacity>
                <TouchableOpacity onPress={() => setPassword('')}>
                  <Image
                    source={require('@/assets/images/right-icon-wrapper.png')}
                    style={styles.iconButton}
                  />
                </TouchableOpacity>
              </>
            ) : (
              <TouchableOpacity onPress={() => setShowPassword(!showPassword)}>
                <Image
                  source={
                    showPassword
                      ? require('@/assets/images/input-field-icon.png')
                      : require('@/assets/images/right-icon-wrapper-2.png')
                  }
                  style={styles.iconButton}
                />
              </TouchableOpacity>
            )}
          </View>

          <View style={styles.optionRow}>
            <TouchableOpacity
              style={styles.autoLoginRow}
              onPress={() => setAutoLoginChecked(prev => !prev)}
            >
              <Image
                source={
                  autoLoginChecked
                    ? require('@/assets/images/checkbox.png')
                    : require('@/assets/images/checkbox-unselected.png')
                }
                style={styles.autoLoginIcon}
              />
              <Text style={styles.optionText}>자동 로그인</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.findPasswordRow}>
              <Text style={styles.optionText}>비밀번호 찾기</Text>
              <Image
                source={require('@/assets/images/text-button-icon.png')}
                style={styles.findIcon}
              />
            </TouchableOpacity>
          </View>

          <TouchableOpacity style={styles.loginButton} onPress={handleLogin}>
            <Text style={styles.loginButtonText}>이메일 로그인</Text>
          </TouchableOpacity>

          <View style={styles.divider}>
            <View style={styles.line} />
            <Text style={styles.orText}>또는</Text>
            <View style={styles.line} />
          </View>

          <TouchableOpacity style={styles.kakaoButton}>
            <Image
              source={require('@/assets/images/icon-kakao-36.png')}
              style={styles.kakaoIcon}
            />
            <Text style={styles.kakaoText}>카카오로 시작하기</Text>
          </TouchableOpacity>

          <View style={styles.signUpWrapper}>
            <TouchableOpacity onPress={() => router.push('/(auth)/join/email')}>
              <Text style={styles.optionText}>
                아직 회원이 아니신가요? <Text style={styles.signUpText}>회원가입</Text>
              </Text>
            </TouchableOpacity>
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: { flex: 1, backgroundColor: '#FFFFFF' },
  scroll: { flex: 1 },
  header: { paddingHorizontal: 20, marginTop: 44 },
  title: { fontSize: 20, fontWeight: 'bold', color: '#111111' },
  logoWrapper: { alignItems: 'center', marginTop: 16 },
  logo: { height: 80, resizeMode: 'contain', marginBottom: 12 },
  form: { paddingHorizontal: 20 },
  label: { fontSize: 14, marginBottom: 9, color: '#111111' },
  passwordLabel: { marginTop: 16 },
  inputWrapper: {
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#E5E5EC',
    borderRadius: 12,
    paddingVertical: 4,
    paddingHorizontal: 16,
    backgroundColor: '#FFFFFF',
  },
  textInput: {
    flex: 1,
    fontSize: 16,
    color: '#111111',
    paddingVertical: 10,
  },
  iconButton: {
    width: 28,
    height: 28,
    resizeMode: 'contain',
  },
  iconSpacing: {
    marginRight: 8,
  },
  optionRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 12,
  },
  autoLoginRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  autoLoginIcon: {
    width: 20,
    height: 20,
    marginRight: 8,
  },
  optionText: {
    color: '#767676',
    fontSize: 14,
  },
  findPasswordRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  findIcon: {
    width: 12,
    height: 12,
    marginLeft: 4,
  },
  loginButton: {
    backgroundColor: '#F7F7FB',
    borderRadius: 12,
    alignItems: 'center',
    paddingVertical: 14,
    marginTop: 36,
  },
  loginButtonText: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  divider: {
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 24,
  },
  line: {
    flex: 1,
    height: 1,
    backgroundColor: '#E5E5EC',
  },
  orText: {
    marginHorizontal: 12,
    color: '#767676',
    fontSize: 14,
  },
  kakaoButton: {
    backgroundColor: '#FEE500',
    borderRadius: 12,
    alignItems: 'center',
    paddingVertical: 14,
    flexDirection: 'row',
    justifyContent: 'center',
  },
  kakaoIcon: {
    width: 20,
    height: 20,
    marginRight: 8,
  },
  kakaoText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#191919',
  },
  signUpWrapper: {
    alignItems: 'center',
    marginTop: 32,
  },
  signUpText: {
    color: '#2379FA',
    fontWeight: 'bold',
  },
});

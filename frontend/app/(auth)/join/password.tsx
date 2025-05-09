import React, { useState, useEffect } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  StyleSheet,
  Alert,
  BackHandler,
} from 'react-native';
import { router, useLocalSearchParams } from 'expo-router';
import { signup } from '@/api/Auth';

export default function PasswordSetupScreen() {
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const { email } = useLocalSearchParams<{ email: string }>();

  const isValidPassword = (pw: string) =>
    pw.length >= 8 &&
    pw.length <= 12 &&
    /[a-zA-Z]/.test(pw) &&
    /\d/.test(pw) &&
    /[^a-zA-Z0-9]/.test(pw);

  const isValid = isValidPassword(password);
  const showValidation = password.length > 0;

  useEffect(() => {
    const backAction = () => true;
    const backHandler = BackHandler.addEventListener('hardwareBackPress', backAction);
    return () => backHandler.remove();
  }, []);

  const handleComplete = async () => {
    if (!email || !password) {
      Alert.alert('오류', '이메일 또는 비밀번호가 누락되었습니다.');
      return;
    }

    if (!isValid) {
      Alert.alert('오류', '비밀번호는 영어, 숫자, 특수문자를 포함한 8~12자여야 합니다.');
      return;
    }

    try {
      setLoading(true);
      await signup({ email, password });
      Alert.alert('회원가입 완료', '이제 로그인할 수 있어요!');
      router.replace('/login');
    } catch (error: any) {
      Alert.alert(
        '회원가입 실패',
        error.response?.data?.message || '문제가 발생했습니다. 다시 시도해 주세요.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => router.back()}>
          <Image
            source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
            style={styles.backIcon}
          />
        </TouchableOpacity>
        <Text style={styles.title}>회원가입</Text>
      </View>

      <View style={styles.progressBar} />

      <View style={styles.content}>
        <Text style={styles.heading}>로그인에 사용할{'\n'}비밀번호를 입력해 주세요</Text>
        <Text style={styles.sub}>
          영어, 숫자, 특수문자를 포함한 8~12자를 입력해 주세요!
        </Text>

        <View
          style={[
            styles.inputWrapper,
            showValidation && (isValid ? styles.validInput : styles.invalidInput),
          ]}
        >
          <TextInput
            placeholder="비밀번호를 입력해 주세요"
            secureTextEntry={!showPassword}
            value={password}
            onChangeText={setPassword}
            style={styles.input}
          />
          <TouchableOpacity onPress={() => setShowPassword(prev => !prev)}>
            <Image
              source={
                showPassword
                  ? require('@/assets/images/input-field-icon.png')
                  : require('@/assets/images/right-icon-wrapper-2.png')
              }
              style={styles.eyeIcon}
            />
          </TouchableOpacity>
        </View>

        {showValidation && (
          <Text style={isValid ? styles.validText : styles.invalidText}>
            {isValid
              ? '사용 가능한 비밀번호입니다.'
              : '영어, 숫자, 특수문자를 포함한 8~12자를 입력해 주세요.'}
          </Text>
        )}

        <TouchableOpacity
          style={[
            styles.button,
            { backgroundColor: isValid ? '#2379FA' : '#F7F7FB' },
          ]}
          onPress={handleComplete}
          disabled={!isValid || loading}
        >
          <Text
            style={[
              styles.buttonText,
              { color: isValid ? '#fff' : '#999999' },
            ]}
          >
            {loading ? '처리 중...' : '완료'}
          </Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#FFFFFF' },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingTop: 44,
    paddingHorizontal: 20,
  },
  backIcon: { width: 36, height: 44 },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#111',
    marginLeft: 12,
  },
  progressBar: {
    height: 6,
    backgroundColor: '#2379FA',
    borderRadius: 100,
    marginHorizontal: 20,
    marginTop: 8,
  },
  content: {
    paddingHorizontal: 20,
    paddingTop: 32,
  },
  heading: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#111',
    marginBottom: 8,
  },
  sub: {
    fontSize: 14,
    color: '#767676',
    marginBottom: 32,
  },
  inputWrapper: {
    flexDirection: 'row',
    alignItems: 'center',
    borderColor: '#E5E5EC',
    borderWidth: 1,
    borderRadius: 12,
    paddingHorizontal: 16,
    paddingVertical: 4,
    marginBottom: 8,
  },
  validInput: {
    borderColor: '#00C851',
    borderWidth: 1.5,
  },
  invalidInput: {
    borderColor: '#FF4444',
    borderWidth: 1.5,
  },
  input: {
    flex: 1,
    fontSize: 16,
    color: '#111',
    paddingVertical: 10,
  },
  eyeIcon: {
    width: 28,
    height: 28,
    resizeMode: 'contain',
  },
  validText: {
    color: '#00C851',
    fontSize: 13,
    marginBottom: 20,
  },
  invalidText: {
    color: '#FF4444',
    fontSize: 13,
    marginBottom: 20,
  },
  button: {
    borderRadius: 12,
    alignItems: 'center',
    paddingVertical: 14,
  },
  buttonText: {
    fontSize: 16,
    fontWeight: 'bold',
  },
});

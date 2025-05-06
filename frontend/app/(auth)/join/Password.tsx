import React, { useState } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  StyleSheet,
} from 'react-native';
import { router } from 'expo-router';

export default function PasswordSetupScreen() {
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const handleComplete = () => {
    // TODO: 비밀번호 저장 및 회원가입 완료 처리
    router.replace('/');
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
        <Text style={styles.heading}>
          로그인에 사용할{'\n'}비밀번호를 입력해 주세요
        </Text>
        <Text style={styles.sub}>
          영어, 숫자, 특수문자를 포함한 8~12자를 입력해 주세요!
        </Text>

        <View style={styles.inputWrapper}>
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

        <TouchableOpacity
          style={[
            styles.button,
            { backgroundColor: password ? '#2379FA' : '#F7F7FB' },
          ]}
          onPress={handleComplete}
          disabled={!password}
        >
          <Text
            style={[
              styles.buttonText,
              { color: password ? '#fff' : '#999999' },
            ]}
          >
            완료
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
  title: { fontSize: 20, fontWeight: 'bold', color: '#111' },
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
    marginBottom: 32,
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

import React, { useEffect, useState } from 'react';
import {
    SafeAreaView,
    View,
    Text,
    TouchableOpacity,
    ScrollView,
    Image,
    StyleSheet,
    Alert,
} from 'react-native';
import { useLocalSearchParams, useRouter } from 'expo-router';
import { recommendCertificates, saveSelectedCertificates } from '@/api/Onboarding';

type CertificateItem = {
    certificateName: string;
};

export default function OnboardingCertificateScreen() {
    const router = useRouter();
    const { jobName } = useLocalSearchParams<{ jobName: string }>();
    const [certificates, setCertificates] = useState<CertificateItem[]>([]);
    const [selected, setSelected] = useState<string[]>([]);
    const canSubmit = certificates.length === 0 || selected.length > 0;

    useEffect(() => {
        (async () => {
            try {
                const result = await recommendCertificates();
                setCertificates(result);
            } catch (e) {
                Alert.alert('자격증 추천 실패', 'AI 추천 정보를 불러오지 못했어요.');
            }
        })();
    }, []);

    const toggleCertificate = (name: string) => {
        setSelected(prev =>
            prev.includes(name)
                ? prev.filter(item => item !== name)
                : [...prev, name]
        );
    };

    const handleSubmit = async () => {
        try {
            await saveSelectedCertificates(selected);
            router.push('/onboarding/loading');
        } catch (e) {
            Alert.alert('저장 실패', '선택한 자격증 저장에 실패했어요.');
        }
    };

    return (
        <SafeAreaView style={styles.container}>
            <ScrollView style={styles.scroll}>
                <View style={styles.header}>
                    <TouchableOpacity onPress={() => router.back()}>
                        <Image
                            source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
                            style={styles.backIcon}
                        />
                    </TouchableOpacity>
                    <View style={styles.progressBarBackground}>
                        <View style={styles.progressBarFill} />
                    </View>
                    <Text style={styles.title}>추천 자격증 중,{"\n"}목표하는 것을 선택해보세요!</Text>
                    <View style={styles.jobWrapper}>
                        <Text style={styles.asterisk}>*</Text>
                        <Text style={styles.jobText}>{jobName}</Text>
                    </View>
                </View>

                <View style={styles.listWrapper}>
                    {certificates.length === 0 && (
                        <Text style={{ fontSize: 14, color: '#767676', marginBottom: 16 }}>
                            추천된 자격증이 없어요. 이대로 저장하셔도 괜찮습니다.
                        </Text>
                    )}

                    {certificates.map(({ certificateName }) => {
                        const isSelected = selected.includes(certificateName);
                        return (
                            <TouchableOpacity
                                key={certificateName}
                                onPress={() => toggleCertificate(certificateName)}
                                style={[
                                    styles.itemBox,
                                    isSelected && styles.itemBoxSelected,
                                ]}
                            >
                                <Text style={styles.itemText}>{certificateName}</Text>
                                <Image
                                    source={
                                        isSelected
                                            ? require('@/assets/images/checkbox.png')
                                            : require('@/assets/images/checkbox-unselected.png')
                                    }
                                    style={styles.checkbox}
                                />
                            </TouchableOpacity>
                        );
                    })}
                </View>


                <View style={styles.infoBox}>
                    <View style={styles.infoRow}>
                        <Image
                            source={require('@/assets/images/tossface-qurious-100.png')}
                            style={styles.infoImage}
                        />
                        <Text style={styles.infoTitle}>원하는 자격증이 보이지 않나요?</Text>
                    </View>
                    <Text style={styles.infoText}>
                        관련 데이터가 충분하지 않을 경우, 원하는 추천 자격증이 보이지 않을 수 있어요.
                        나중에 커리어셋 화면에서 직접 추가할 수 있어요!
                    </Text>
                </View>


                <TouchableOpacity
                    onPress={handleSubmit}
                    disabled={!canSubmit}
                    style={[
                        styles.button,
                        { backgroundColor: canSubmit ? '#2379FA' : '#F7F7FB' },
                    ]}
                >
                    <Text
                        style={[
                            styles.buttonText,
                            { color: canSubmit ? '#FFFFFF' : '#999999' },
                        ]}
                    >
                        목표 설정 완료하기
                    </Text>
                </TouchableOpacity>

            </ScrollView>
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#FFFFFF' },
    scroll: { flex: 1 },
    header: { paddingTop: 44, paddingHorizontal: 20, marginBottom: 12 },
    backIcon: { width: 28, height: 28, marginBottom: 16 },
    progressBarBackground: {
        height: 6,
        backgroundColor: '#F1F1F5',
        borderRadius: 100,
        marginBottom: 32,
    },
    progressBarFill: {
        width: '100%',
        height: 6,
        backgroundColor: '#2379FA',
        borderRadius: 100,
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#111111',
        marginBottom: 8,
    },
    jobWrapper: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    asterisk: {
        color: '#2379FA',
        fontSize: 14,
        marginRight: 4,
    },
    jobText: {
        color: '#767676',
        fontSize: 14,
    },
    listWrapper: {
        marginTop: 20,
        marginBottom: 32,
        paddingHorizontal: 20,
    },
    itemBox: {
        flexDirection: 'row',
        alignItems: 'center',
        borderColor: '#F1F1F5',
        borderWidth: 1,
        borderRadius: 12,
        paddingVertical: 14,
        paddingHorizontal: 16,
        marginBottom: 12,
        backgroundColor: '#FFFFFF',
    },
    itemBoxSelected: {
        borderColor: '#2379FA',
        backgroundColor: '#E8F0FF',
    },
    itemText: {
        color: '#111111',
        fontSize: 16,
        flex: 1,
    },
    checkbox: {
        width: 20,
        height: 20,
    },
    infoBox: {
        backgroundColor: '#F7F7FB',
        borderRadius: 12,
        paddingVertical: 20,
        paddingHorizontal: 16,
        marginHorizontal: 20,
        marginBottom: 40,
        alignItems: 'flex-start',
    },
    infoRow: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 12,
    },
    infoImage: {
        width: 24,
        height: 24,
        marginRight: 8,
    },
    infoTitle: {
        fontSize: 14,
        fontWeight: 'bold',
        color: '#111111',
    },
    infoText: {
        fontSize: 14,
        color: '#767676',
        lineHeight: 22,
        textAlign: 'left',
    },
    button: {
        borderRadius: 12,
        alignItems: 'center',
        paddingVertical: 14,
        marginHorizontal: 20,
        marginBottom: 40,
    },
    buttonText: {
        fontSize: 16,
        fontWeight: '700',
    },
});

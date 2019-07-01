Reupload emedia project.


Whole project was devided into two parts:
1) decoding of .wav file and getting important values of file from header, then plot a few samples using FFT (Fast Fourier Transform).

Used outside files:

JUnit GraphicPanel for showing graph:
class source code: https://gist.github.com/danleyb2/f6745af603d53e49a839

FFT class:
source code: https://github.com/niangaotuantuan/Android-AudioAugmentation/blob/master/Subspect/src/com/example/subspectrum/FFTbase.java

2) encrypting and decrypting .wav file soundtrack with RSA algorithm. Saved in local dir as encrypted.wav, decrypted.wav.
Returned file works fine, but its sound is noised a lot, despite this its available to hear initial soundtrack.

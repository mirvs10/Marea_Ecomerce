function togglePassword(inputId) {
            const field = document.getElementById(inputId);
            field.type = field.type === 'password' ? 'text' : 'password';
        }
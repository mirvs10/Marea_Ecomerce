function togglePassword(inputId) {
            const passwordField = document.getElementById(inputId);
            if (passwordField) {
                passwordField.type = passwordField.type === 'password' ? 'text' : 'password';
            }
        }
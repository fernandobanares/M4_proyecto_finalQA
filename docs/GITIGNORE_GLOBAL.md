# Configuración de .gitignore Global

## Crear .gitignore global

Para evitar que archivos del sistema se incluyan en cualquier repositorio, puedes configurar un `.gitignore` global:

### 1. Crear el archivo global
```bash
# En Windows
echo. > %USERPROFILE%\.gitignore_global

# En Linux/Mac
touch ~/.gitignore_global
```

### 2. Configurar Git para usar el archivo global
```bash
git config --global core.excludesfile ~/.gitignore_global
```

### 3. Contenido recomendado para .gitignore_global

```gitignore
# OS generated files
.DS_Store
.DS_Store?
._*
.Spotlight-V100
.Trashes
ehthumbs.db
Thumbs.db
desktop.ini
$RECYCLE.BIN/

# Editor files
*~
*.swp
*.swo
.#*
\#*#
*.sublime-project
*.sublime-workspace
.vscode/
.idea/

# Temporary files
*.tmp
*.temp
*.log
*.bak
*.backup
*.orig

# System files
Thumbs.db
.DS_Store
.AppleDouble
.LSOverride
Icon?
._*
.DocumentRevisions-V100
.fseventsd
.Spotlight-V100
.TemporaryItems
.Trashes
.VolumeIcon.icns
.com.apple.timemachine.donotpresent
.AppleDB
.AppleDesktop
Network Trash Folder
Temporary Items
.apdisk
```

## Verificar configuración

```bash
# Ver configuración actual
git config --global core.excludesfile

# Verificar que funciona
git status
```

## Comandos útiles para .gitignore

```bash
# Ver archivos ignorados
git status --ignored

# Forzar agregar archivo ignorado
git add -f archivo_ignorado.txt

# Dejar de trackear archivo ya commiteado
git rm --cached archivo.txt

# Limpiar cache de git (útil después de actualizar .gitignore)
git rm -r --cached .
git add .
git commit -m "Update .gitignore"
```